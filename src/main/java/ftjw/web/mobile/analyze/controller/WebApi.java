package ftjw.web.mobile.analyze.controller;

import ftjw.web.mobile.analyze.dao.DataRepository;
import ftjw.web.mobile.analyze.dao.SaleRepository;
import ftjw.web.mobile.analyze.entity.SaleMan;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 殷晓龙
 * 2020/3/16 17:51
 */
@Controller
@RequestMapping("/web")
public class WebApi {

    @Resource
    SaleRepository saleRepository;
    @Resource
    DataRepository dataRepository;

    @RequestMapping("/index")
    public String index(){

        return "index";
    }

    @RequestMapping("/login")
    public String logiin(){

        return "index";
    }

    @RequestMapping("/analyze")
    public String analyze(){
        return "analyze";
    }

    @RequestMapping("/submit")
    public String submit(){
        return "submit";
    }



    @RequestMapping("/{id}/check")
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
