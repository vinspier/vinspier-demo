package com.vinspier.demo.util;

/**
 * @ClassName: ResponseTemplate
 * @Description: 简单的返回数据模板
 * @Author:
 * @Date: 2019/12/2 13:53
 * @Version V1.0
 **/
public class ResponseTemplate {

    private String message;

    private String errCode;

    private Object data;

    private boolean success;

    private Integer totalCount;
    private Integer pageSize;
    private Integer pageNo;

    private ResponseTemplate() {
    }

    private ResponseTemplate(boolean success){
        this.success = success;
    }

    public static ResponseTemplate createOk(){
      return   new ResponseTemplate(true);
    }

    public static ResponseTemplate createError(){
        return new ResponseTemplate(false);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void falied(){
        this.success = false;
    }

    public void success(){
        this.success = true;
    }
}
