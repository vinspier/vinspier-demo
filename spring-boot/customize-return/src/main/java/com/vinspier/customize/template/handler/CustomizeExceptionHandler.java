package com.vinspier.customize.template.handler;

import com.vinspier.customize.template.common.ResponseTemplate;
import com.vinspier.customize.template.common.ResultCode;
import com.vinspier.customize.template.exception.CustomizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: CustomizeExceptionHandler
 * @Description: 自定义全局异常处理类
 * @Author:
 * @Date: 2020/3/19 11:29
 * @Version V1.0
 **/

@ControllerAdvice
public class CustomizeExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomizeExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseTemplate handleException(Exception e){
        logger.error(e.getMessage(),e);
        return ResponseTemplate.failed(ResultCode.SERVER_UNKNOW_ERROR);
    }

    @ExceptionHandler(value = CustomizeException.class)
    @ResponseBody
    public ResponseTemplate handleUserException(CustomizeException e){
        logger.error(e.getMessage(),e);
        return ResponseTemplate.failed(e.getResultCode());
    }

}
