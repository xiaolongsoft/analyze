package ftjw.web.mobile.analyze.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 殷晓龙
 * 2020/3/16 17:51
 */
@Controller
@RequestMapping("/web")
public class WebApi {

    @RequestMapping("")
    public String index(){
        return "index";
    }
}
