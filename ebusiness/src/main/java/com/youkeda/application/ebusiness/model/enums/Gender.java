package com.youkeda.application.ebusiness.model.enums;

public enum Gender {
    //男性
    MALE("male"),
    //女性
    FEMALE("female"),
    //未知
    UNKNOW("unknow");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return value;
    }
}
