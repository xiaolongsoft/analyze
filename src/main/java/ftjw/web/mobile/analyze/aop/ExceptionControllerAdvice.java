package ftjw.web.mobile.analyze.aop;

import ftjw.web.mobile.analyze.core.Result;
import ftjw.web.mobile.analyze.core.ResultGenerator;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 * 殷晓龙
 * 2020/4/9 16:40
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        ObjectError oe=e.getBindingResult().getAllErrors().get(0);
        return ResultGenerator.genFailResult(oe.getDefaultMessage());
    }
    @ExceptionHandler(BindException.class)
    public Result BindExceptionHandler(BindException e){
        ObjectError oe=e.getBindingResult().getAllErrors().get(0);
        return ResultGenerator.genFailResult(oe.getDefaultMessage());
    }
}
