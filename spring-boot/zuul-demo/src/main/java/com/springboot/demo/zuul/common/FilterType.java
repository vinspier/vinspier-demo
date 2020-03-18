package com.springboot.demo.zuul.common;

public enum FilterType {
    PRE("pre"),
    ROUTE("route"),
    POST("post"),
    ERROR("error");

    private String type;

    private FilterType(String type) {
        this.type = type;
    }

    public String type(){
        return this.type;
    }
}
