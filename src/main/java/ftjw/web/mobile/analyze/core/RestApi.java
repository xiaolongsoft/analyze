package ftjw.web.mobile.analyze.core;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import ftjw.web.mobile.analyze.dao.AgentRepository;
import ftjw.web.mobile.analyze.dao.DataRepository;
import ftjw.web.mobile.analyze.dao.SubmitRepository;
import ftjw.web.mobile.analyze.entity.Agent;
import ftjw.web.mobile.analyze.entity.AnalyzeData;
import ftjw.web.mobile.analyze.entity.AnalyzeSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
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

    @RequestMapping("")
    public String test(){
        return "ok";
    }
    @RequestMapping("/p")
    public String testPost(String name,Integer id){
        return "name:"+name+" ,id:"+id;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Page list(@RequestParam(required = false) String keywords,@RequestParam(required = false) Integer status, @RequestParam(defaultValue = "0") Integer pageIndex,@RequestParam (defaultValue = "20") Integer pageSize){
        PageRequest request= PageRequest.of(pageIndex,pageSize, Sort.by("id"));
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

    /**
     * 代理商申请
     * @return
     */
    @PostMapping("/agent/apply")
    public Result agentApply(Agent agent){
        try {
            agent.setCtime(new Date());
            agentRepository.save(agent);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(agent);
    }

    @PostMapping("/agent/update")
    public Result updateAgent(@RequestParam(name = "id") Integer id,@RequestParam(name = "status") Integer status){
        if(id==null){
            return ResultGenerator.genEmptyResult("少id啊");
        }

        Optional<Agent> op = agentRepository.findById(id);
        Agent  agent = op.get();
        agentRepository.save(agent);
        return ResultGenerator.genSuccessResult();
    }


    private void genAccountPassword(Agent agent){
        agent.setAccount(ChineseCharacterUtil.getLowerCase(agent.getName(),false)+agent.getId());
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder();
        agent.setPassword(bc.encode("123456"));
    }

}
