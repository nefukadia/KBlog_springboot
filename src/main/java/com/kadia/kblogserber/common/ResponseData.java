package com.kadia.kblogserber.common;

public class ResponseData {
    private int code;
    private String msg;
    private long time;
    private Object data;

    public ResponseData(int code, String msg, long time, Object data) {
        this.code = code;
        this.msg = msg;
        this.time = time;
        this.data = data;
    }

    public static ResponseData success(Object data, String msg){
        return new ResponseData(1, msg, Timer.getTime(), data);
    }

    public static ResponseData success(Object data){
        return new ResponseData(1, "success",Timer.getTime(), data);
    }

    public static ResponseData success(){
        return new ResponseData(1, "success",Timer.getTime(), null);
    }


    public static ResponseData error(String msg){
        return new ResponseData(0, msg,Timer.getTime(), null);
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
