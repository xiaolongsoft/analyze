package ftjw.web.mobile.analyze.controller;

import ftjw.web.mobile.analyze.dao.SaleRepository;
import ftjw.web.mobile.analyze.entity.SaleMan;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 殷晓龙
 * 2020/3/16 17:51
 */
@Controller
@Api(tags = "网页端相关方法")
@RequestMapping("/web")
public class WebApi {

    @Resource
    SaleRepository saleRepository;

    @GetMapping("/index")
    public String index(){
        return "web_index";
    }

    @GetMapping("/analyze")
    public String analyze(){
        return "analyze";
    }

    @GetMapping("/submit")
    public String submit(){
        return "submit";
    }



    @GetMapping("/{id}/check")
    public String check(@PathVariable(name = "id",required = false) Integer id, @RequestParam(name = "referrer",required = false) String referrer
            ,@RequestParam(name = "phone",required = false) String phone,  HttpServletRequest request){
        request.setAttribute("saleid",id);
        Optional<SaleMan> saleMan = saleRepository.findById(id);
        request.setAttribute("tel",saleMan.get().getPhone());
        request.setAttribute("referrer",referrer);
        request.setAttribute("phone",phone);
        return "checkv2";
    }
}
