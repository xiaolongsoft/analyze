package ftjw.web.mobile.analyze.core;

import ftjw.web.mobile.analyze.dao.DataRepository;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 殷晓龙
 * 2020/3/16 17:47
 */
@RestController
@RequestMapping("/api")
public class RestApi {

    @Resource
    private DataRepository dataRepository;

    @RequestMapping("")
    public String test(){
        return "ok";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Page list(@RequestParam(required = false) String keywords, @RequestParam(defaultValue = "0") Integer pageIndex,@RequestParam (defaultValue = "20") Integer pageSize){
        PageRequest request= PageRequest.of(pageIndex,pageSize, Sort.by("id"));
        AnalyzeData analyzeData=new AnalyzeData();
        analyzeData.setName(keywords);
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        Example exp=Example.of(analyzeData,matcher);
        Page page=dataRepository.findAll(exp,request);
        return page;
    }
}
