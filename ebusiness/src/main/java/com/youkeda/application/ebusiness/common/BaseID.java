package com.youkeda.application.ebusiness.common;

public class BaseID<T> extends BaseDate {

    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
