package ftjw.web.mobile.analyze.core;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import ftjw.web.mobile.analyze.dao.DataRepository;
import ftjw.web.mobile.analyze.dao.SubmitRepository;
import ftjw.web.mobile.analyze.entity.AnalyzeData;
import ftjw.web.mobile.analyze.entity.AnalyzeSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
     * @param message
     * @param phone
     * @return
     */
    @RequestMapping("/submit")
    public  AnalyzeSubmit submitInfo(@RequestParam(name = "message",defaultValue = "无")String message, @RequestParam(name = "phone")String phone, @RequestParam(name = "saleid",defaultValue = "0") Integer id){

        if(StringUtils.length(phone)<11){
            return null;
        }
        AnalyzeSubmit submit=new AnalyzeSubmit();
        submit.setMessage(message);
        submit.setPhone(phone);
        submit.setSid(id);
        submitRepository.save(submit);
        Map parms=new HashMap();
        parms.put("custname",message);
        parms.put("tel",phone);
        parms.put("userid",id);
        String res = HttpUtil.get("http://111.198.66.100:7180/xiansuo/leads", parms);
        log.info("新用户提交记录",res);
        return submit;
    }

}
