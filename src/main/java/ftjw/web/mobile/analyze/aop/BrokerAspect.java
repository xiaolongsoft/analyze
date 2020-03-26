package ftjw.web.mobile.analyze.aop;

import ftjw.web.mobile.analyze.core.SecurityApi;
import ftjw.web.mobile.analyze.core.SeleniumAnalyze;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 殷晓龙
 * 2020/3/26 14:18
 */
@Aspect
@Component
public class BrokerAspect {
    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * ftjw.web.mobile.analyze.core.SeleniumAnalyze.webUrlCheck(..)))")
    public void BrokerAspect(){

    }

/*    *//**
     * @description  在连接点执行之前执行的通知
     *//*
    @Before("BrokerAspect()")
    public void doBeforeGame(){
        System.out.println("经纪人正在处理球星赛前事务！");
    }

    *//**
     * @description  在连接点执行之后执行的通知（返回通知和异常通知的异常）
     *//*
    @After("BrokerAspect()")
    public void doAfterGame(){
        System.out.println("经纪人为球星表现疯狂鼓掌！");
    }

    *//**
     * @description  在连接点执行之后执行的通知（异常通知）
     *//*
    @AfterThrowing("BrokerAspect()")
    public void doAfterThrowingGame(){
        System.out.println("异常通知：球迷要求退票！");
    }*/

    /**
     * @description  使用环绕通知
     */
    @Around("BrokerAspect()")
    public void doAroundGame(ProceedingJoinPoint pjp) throws Throwable {
        try{
            Object target = pjp.getTarget();
            SeleniumAnalyze s= (SeleniumAnalyze) target;
            pjp.proceed();
            System.out.println("chromeDriver退出");
            s.quite();
        }
        catch(Throwable e){
            System.out.println("获取页面异常");
        }
    }

}
