package ftjw.web.mobile.analyze.core;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 殷晓龙
 * 2020/3/18 10:14
 * 网页分析
 */
public class SeleniumAnalyze {

    public SeleniumAnalyze() {
        this.webDriver = initChromeDriver();
    }

    ChromeDriver webDriver;

    /**
     * 初始化webdriver
     * @return
     */
    public static ChromeDriver initChromeDriver(){
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
        return new ChromeDriver(options);
    }

    /**
     * 检查网站是否支持移动端浏览
     * @param url
     * @return
     */
    public  boolean webUrlCheck(String url){

        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get(url);
        webDriver.executeScript("return document.documentElement.outerHTML");
        List ele= webDriver.findElementsByXPath("//meta[@name='viewport']");
        if(ele.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 获取网站当前移动端截图
     */
    public  String  getBase64ScreenImage(){

      return   webDriver.getScreenshotAs(OutputType.BASE64);

    }

    public void quite(){
        webDriver.quit();
    }
}
