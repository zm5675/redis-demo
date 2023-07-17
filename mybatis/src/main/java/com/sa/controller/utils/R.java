package com.sa.controller.utils;

import lombok.Data;

@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    private R (){

    }
    public static <T> R<T> success(String msg,T t){
        R r = new R();
        r.setData(t);
        r.setCode(1);
        r.setMsg(msg);
        return r;
    }
    public static <T> R<T> mistake(String msg){
        R r = new R();
        r.setMsg(msg);
        r.setCode(0);
        return r;
    }
}
