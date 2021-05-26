package com.lyz.demo5.exception;

import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常捕获类
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {


//    @ExceptionHandler(java.lang.Exception.class)
//    public String  handleRE(Exception ex){
//        System.out.println(ex.getMessage());
//        return "fff";
//    }

    /**
     * 捕获AccessDeniedException 权限不足异常
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResultJson handleAccessRE(AccessDeniedException ex){

        System.out.println(ex.getMessage());

        return ResultJson.error(ResultCode.PAGE_ACCESSDENIED,ex.getMessage());
    }


}
