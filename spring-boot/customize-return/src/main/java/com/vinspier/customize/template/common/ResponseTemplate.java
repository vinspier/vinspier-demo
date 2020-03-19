package com.vinspier.customize.template.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @ClassName: ResponseSuccessTemplate
 * @Description: 自定义全局的成功返回异常
 * @Author:
 * @Date: 2020/3/19 10:22
 * @Version V1.0
 **/
public class ResponseTemplate<T> {

    private  int code;

    private String msg;

    private boolean success;

    private T data;

    private ResponseTemplate(int code,String msg,boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    private ResponseTemplate(T data,int code,String msg,boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public static ResponseTemplate ok(Object data){
        return new ResponseTemplate(data,200,"操作成功",true);
    }

    public static ResponseTemplate failed(ResultCode resultCode){
        return new ResponseTemplate(resultCode.getCode(),resultCode.getMsg(),false);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseSuccessTemplate{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
