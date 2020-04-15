package ftjw.web.mobile.analyze.aop;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import ftjw.web.mobile.analyze.core.WebLog;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Marker;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 殷晓龙
 * 日志监控
 * 2020/4/13 16:33
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * ftjw.web.mobile.analyze.controller.*.*(..))")
    public void webLog(){

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long starttime=System.currentTimeMillis();
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        WebLog weblog =new WebLog();
        Object proceed = pjp.proceed();
        Signature signature = pjp.getSignature();
        MethodSignature  methodSignature= (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if(method.isAnnotationPresent(ApiOperation.class)){
            ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            weblog.setDescription(annotation.value());
        }
        long endtime = System.currentTimeMillis();
        String urlstr=request.getRequestURL().toString();
        weblog.setBasePath(StrUtil.removeSuffix(urlstr, URLUtil.url(urlstr).getPath()));
        weblog.setIp(request.getRemoteUser());
        weblog.setMethod(request.getMethod());
        weblog.setParameter(getParameter(method,pjp.getArgs()));
        weblog.setResult(proceed);
        weblog.setSpendTime((int) (endtime-starttime));
        weblog.setStartTime(starttime);
        weblog.setUri(request.getRequestURI());
        weblog.setUrl(request.getRequestURL().toString());
        log.info(JSONUtil.toJsonStr(weblog));
        return proceed;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
