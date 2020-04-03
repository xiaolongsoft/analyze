package ftjw.web.mobile.analyze.controller;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import ftjw.web.mobile.analyze.core.ChineseCharacterUtil;
import ftjw.web.mobile.analyze.core.Result;
import ftjw.web.mobile.analyze.core.ResultGenerator;
import ftjw.web.mobile.analyze.core.SeleniumAnalyze;
import ftjw.web.mobile.analyze.dao.*;
import ftjw.web.mobile.analyze.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.*;

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



    @RequestMapping("")
    public String test(){
        return "ok";
    }
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
    public Page list(@RequestParam(required = false) String keywords,@RequestParam(required = false) Integer status, @RequestParam(defaultValue = "0") Integer pageIndex,@RequestParam (defaultValue = "20") Integer pageSize){
        PageRequest request= PageRequest.of(pageIndex-1,pageSize, Sort.by("id"));
        AnalyzeData analyzeData=new AnalyzeData();
        analyzeData.setName(keywords);
        analyzeData.setStatus(status);
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains())
                ;
        Example exp=Example.of(analyzeData,matcher);
        Page page=dataRepository.findAll(exp,request);
        return page;
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
    public Map analyzeUrl(String url){
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







    private void genAccountPassword(Agent agent){
        agent.setAccount(ChineseCharacterUtil.getLowerCase(agent.getName(),false)+agent.getId());
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder();
        agent.setPassword(bc.encode("123456"));
    }

}
