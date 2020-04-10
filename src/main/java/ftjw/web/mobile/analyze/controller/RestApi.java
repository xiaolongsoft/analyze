package ftjw.web.mobile.analyze.controller;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import ftjw.web.mobile.analyze.core.Result;
import ftjw.web.mobile.analyze.core.ResultGenerator;
import ftjw.web.mobile.analyze.core.SeleniumAnalyze;
import ftjw.web.mobile.analyze.dao.*;
import ftjw.web.mobile.analyze.entity.*;
import ftjw.web.mobile.analyze.security.IsAdmin;
import ftjw.web.mobile.analyze.security.IsAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * web端无需验证的接口
 * 殷晓龙
 * 2020/3/16 17:47
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class RestApi {

    @Resource
    private DataRepository dataRepository;
    @Resource
    private SubmitRepository submitRepository;

    @Resource
    private AgentRepository agentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @PostMapping("/login")
//    public Result logiin(@RequestParam(name = "username")String username,@RequestParam(name = "password") String password){
//        Agent account = agentRepository.findOneByAccount(username);
//        if(account==null){
//            return ResultGenerator.genFailResult("账号/密码错误。");
//        }
//        boolean matches = passwordEncoder.matches(password, account.getPassword());
//        if(matches){
//            Map map=new HashMap();
//            map.put("username",username);
//            map.put("token",passwordEncoder.encode(username));
//            map.put("role",account.getRole());
//            return ResultGenerator.genSuccessResult(map);
//        }else {
//            return ResultGenerator.genFailResult("账号/密码错误。");
//        }
//
//    }


    @RequestMapping("")
    @IsAdmin
    public UsernamePasswordAuthenticationToken test(@AuthenticationPrincipal UsernamePasswordAuthenticationToken token){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return token;
    }

    @IsAgent
    @RequestMapping("/p")
    public String testPost(String name,Integer id){
        return "name:"+name+" ,id:"+id;
    }

    /**
     * 移动化网站分析统计
     * @param keywords
     * @param status
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestParam(required = false) String keywords,@RequestParam(required = false) Integer status, @RequestParam(defaultValue = "1") Integer pageIndex,@RequestParam (defaultValue = "20") Integer pageSize){
        PageRequest request= PageRequest.of(pageIndex-1,pageSize, Sort.by("id"));
        AnalyzeData analyzeData=new AnalyzeData();
        analyzeData.setName(keywords);
        analyzeData.setStatus(status);
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains())
                ;
        Example exp=Example.of(analyzeData,matcher);
        Page page=dataRepository.findAll(exp,request);
        return ResultGenerator.genSuccessResult(page);
    }
    @RequestMapping("/analyzeCount")
    @ResponseBody
    public List<Map<String,Object>> analyzeData(){
        List<Map<String, Object>> maps = dataRepository.countAnalyzeDataByStatus();
       return maps;
    }


    @Resource
    UrlRepository urlRepository;

    @RequestMapping("/check")
    @ResponseBody
    public Map analyzeUrl(@RequestParam(name = "url") String url,@RequestParam(name = "sid") Integer sid){
        url=URLUtil.normalize(url);
        Map map=new HashMap();
        SeleniumAnalyze seleniumAnalyze=new SeleniumAnalyze();
        boolean flag;
        try {
            flag = seleniumAnalyze.webUrlCheck(url);
        } catch (Exception e) {
            map.put("score",0);
            return  map;
        }
        if(flag){
            map.put("score",  new Random().nextInt(90-60+1)+60);
        }else {
            map.put("score",new Random().nextInt(31));
        }
        UrlAccessLog urlAccessLog=new UrlAccessLog();
        urlAccessLog.setDate(new Date());
        urlAccessLog.setUrl(url);
        urlAccessLog.setSid(sid);
        urlRepository.save(urlAccessLog);
        return map;
    }

    /**
     * 客户提交信息
     * @return
     */
    @RequestMapping("/submit")
    public Result submitInfo(AnalyzeSubmit submit) {

        try {
            submitRepository.save(submit);
        } catch (ConstraintViolationException e) {
          log.info("提交格式不正确");
          return ResultGenerator.genFailResult("提交格式不正确");
        }
        Map parms=new HashMap();
        parms.put("custname",submit.getName());
        parms.put("tel",submit.getPhone());
        parms.put("userid",submit.getSid());
        parms.put("phone",submit.getRftel());
        parms.put("referrer",submit.getReferrer());
        String res = HttpUtil.get("http://111.198.66.100:7180/xiansuo/leads", parms);
        log.info("新用户提交记录",res);
        return ResultGenerator.genSuccessResult("提交成功");
    }

    @Resource
    SaleRepository saleRepository;

    @PostMapping("/url/access/log")
    public Result urlAccessLog(@RequestParam(name = "sid",required = false) Integer sid,@RequestParam(defaultValue = "1") Integer pageIndex,@RequestParam (defaultValue = "20") Integer pageSize){
        PageRequest request= PageRequest.of(pageIndex-1,pageSize, Sort.by(Sort.Direction.DESC,"id"));
        UrlAccessLog ual=new UrlAccessLog();
        ual.setSid(sid);
        Page<UrlAccessLog> all = urlRepository.findAll(Example.of(ual), request);
        List<SaleMan> saleManList = saleRepository.findAll();
        Map<Integer, String> collect = saleManList.stream().collect(Collectors.toMap(SaleMan::getId, SaleMan::getName));
        for (UrlAccessLog u : all) {
            u.setName(collect.get(u.getSid()));
        }
        List l=new ArrayList();
        l.add(all);
        l.add(saleManList);
        return ResultGenerator.genSuccessResult(l);
    }


}
