package com.vinspier.aop.enums;

public enum  SystemLogTypeEnum {
    OPERATION("1"),
    ERROR("2"),
    LOG_EVENT_CLIENT("3");


    SystemLogTypeEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
