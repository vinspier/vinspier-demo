package com.vinspier.customize.template.common;

/**
 * @ClassName: ResultCode
 * @Description: 定义全局的异常返回模板
 * @code 异常代码
 * @msg 异常信息
 * @Author:
 * @Date: 2020/3/19 10:04
 * @Version V1.0
 **/
public enum ResultCode {

    FETCH_DATA_NONE(2004,"请求成功，未获取到对应数据"),
    PARAMETER_WRONG(4001,"参数校验异常"),
    SERVER_UNKNOW_ERROR(5000,"系统异常"),
    ACCESS_TOKEN_NONE(5001,"缺少身份认证令牌access token"),
    USER_NOT_EXIST(5002,"用户不存在"),
    USERNAME_PASSWORD_WRONG(5003,"账号密码或密码错误");

    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
