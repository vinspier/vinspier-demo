package com.vinspier.customize.template.exception;

import com.vinspier.customize.template.common.ResultCode;

/**
 * @ClassName: CustomizeException
 * @Description:
 * @Author:
 * @Date: 2020/3/19 11:22
 * @Version V1.0
 **/
public class CustomizeException extends RuntimeException{

    private ResultCode resultCode;

    public CustomizeException(ResultCode resultCode) {
        super();
        this.resultCode = resultCode;
    }

    public CustomizeException(String message,ResultCode resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    public CustomizeException(String message, Throwable cause,ResultCode resultCode) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
