package com.tt.teach.until;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JsonResult {
    //响应状态码
    private Integer ststus;
    //响应消息
    private String msg;
    //响应数据
    private Object myData;

    public Integer getStstus() {
        return ststus;
    }

    public void setStstus(Integer ststus) {
        this.ststus = ststus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getMyData() {
        return myData;
    }

    public void setMyData(Object myData) {
        this.myData = myData;
    }

    public JsonResult(Integer ststus, String msg, Object myData) {
        this.ststus = ststus;
        this.msg = msg;
        this.myData = myData;
    }

    public JsonResult() {
    }
    //成功的时候调用
    public static JsonResult ok(String msg,Object myData) {
        return new JsonResult(200,msg,myData);
    }
    //失败的时候调用
    public static JsonResult no(String msg,Object myData) {
        return new JsonResult(502,msg,myData);
    }
}
