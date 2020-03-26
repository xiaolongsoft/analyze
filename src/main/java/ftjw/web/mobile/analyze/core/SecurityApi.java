package ftjw.web.mobile.analyze.core;

import lombok.Data;
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
@Data
public class SecurityApi {
    @RequestMapping("")
    @ResponseBody
    public String go(){

        return "admin ok";
    }



    @RequestMapping("/a")
    @ResponseBody
    public String a(){
        return "admin a";
    }

    @RequestMapping("/b")
    @ResponseBody
    public String b(String name){
        return "admin b namae:"+name;
    }
}

@Controller
@RequestMapping("/user")
class User{

    @RequestMapping("")
    @ResponseBody
    public String go(){
        return "user go 龙";
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


@Controller
@RequestMapping("/err")
 class ErrPage {
    @RequestMapping("")
    @ResponseBody
    public String err(){
        return "应该是出了啥问题";
    }
}