package com.qf.exception;

import com.qf.entity.base.BusinessException;
import com.qf.entity.base.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther JC
 * @date 2020/4/14
 */
@ControllerAdvice //全局异常
public class GlobeExceptionHandler {

    /**
     * 处理系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultEntity systemEx(Exception e){
        e.printStackTrace();
        System.out.println("系统异常:"+e.getMessage());
        return ResultEntity.error(e.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultEntity systemBusiness(BusinessException e){
        System.out.println("业务异常:"+e.getMsg());
        return ResultEntity.error(e.getMsg());
    }
}
