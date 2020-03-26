package ftjw.web.mobile.analyze.core;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 殷晓龙
 * 2020/3/25 16:49
 */
@Controller
@RequestMapping("/admin")
public class SecurityApi {
    @RequestMapping("")
    @ResponseBody
    public String go(){
        return "admin ok";
    }
}

@Controller
@RequestMapping("/user")
class User{

    @RequestMapping("")
    @ResponseBody
    public String go(){
        return "user go";
    }

    @RequestMapping("/info")
    @ResponseBody
    public String productInfo(){
        String currentUser = "";
        Object principl = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principl instanceof UserDetails) {
            currentUser = ((UserDetails)principl).getUsername();
        }else {
            currentUser = principl.toString();
        }
        return " some product info,currentUser is: "+currentUser;
    }

}
