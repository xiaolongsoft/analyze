package ftjw.web.mobile.analyze.core;

import ftjw.web.mobile.analyze.dao.SaleRepository;
import ftjw.web.mobile.analyze.entity.SaleMan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("")
    public String index(){
        return "index";
    }
    @RequestMapping("/{id}/check")
    public String check(@PathVariable(name = "id",required = false) Integer id, HttpServletRequest request){
        request.setAttribute("saleid",id);
        Optional<SaleMan> saleMan = saleRepository.findById(id);
        request.setAttribute("tel",saleMan.get().getPhone());
        return "check";
    }
}
