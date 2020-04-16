package ftjw.web.mobile.analyze;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import ftjw.web.mobile.analyze.core.SeleniumAnalyze;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.ErrorHandler;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 殷晓龙
 * 2020/3/17 17:10
 */
public class AutoTest {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();

        //设置屏幕大小、像素
        deviceMetrics.put("width", 480);
        deviceMetrics.put("height", 720);
        deviceMetrics.put("pixelRatio", 3.0);

        System.setProperty("webdriver.chrome.driver", "E:/python_home/venv/Scripts/chromedriver.exe");
        ChromeOptions options=new ChromeOptions();
        Map<String,Object> mobileEmulation =new HashMap();
        mobileEmulation.put("deviceMetrics",deviceMetrics);
        mobileEmulation.put("userAgent","Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        options.setExperimentalOption("mobileEmulation",mobileEmulation);
        options.addArguments("headless");
        ChromeDriver webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            webDriver.get("http://www.ccccbim.com/");
            webDriver.executeScript("return document.documentElement.outerHTML");
        }catch (Exception e){
            System.out.println("超时了");
            webDriver.quit();
        }

       List ele= webDriver.findElementsByXPath("//meta[@name='viewport']");
        System.out.println(ele.size());
        ErrorHandler errorHandler = webDriver.getErrorHandler();
        Thread.sleep(3000);
        webDriver.quit();

    }

    public void seletest(){
        SeleniumAnalyze seleniumAnalyze=new SeleniumAnalyze();
        try {
            System.out.println(seleniumAnalyze.webUrlCheck("http://aassssaw.com/"));
        } catch (Exception e) {
            System.out.println("无法访问");
        }
        System.out.println(seleniumAnalyze.getBase64ScreenImage());
        seleniumAnalyze.quite();
    }


    @Test
    public  void ibosstoken(){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = sdf.format(new Date(Long.valueOf("1579676066000")));
        System.out.println(format);

    }

    @Test
    public  void genPassword(){
        BCryptPasswordEncoder bce=   new BCryptPasswordEncoder();
        String mima = bce.encode("123456");
        boolean matches = bce.matches("12356", mima);
        Assert.assertTrue(matches);
    }

    @Test
    public  void createUser(){
//        User user =new User();
//        user.setName("晓龙测试");
//        user.setLogin("xiaolong");
//        user.setPassword("123456");
//        user.setServiceid("1");
//        user.setOrgname("晓龙测试testttttttt");
//        Map<String, Object> map = BeanUtil.beanToMap(user,false,true);
//        String user1 = YDZWUtill.createUser(map);
        JSONObject jsonObject = JSONUtil.parseObj("{\"code\":0,\"orgid\":309}");
        System.out.println(jsonObject.get("orgid"));
    }
    @Test
    public  void PageNew(){
        List<String> ls=new ArrayList<>();
        PageImpl pi=new PageImpl(ls, Pageable.unpaged(),10);
        System.out.println(pi);

    }

}
