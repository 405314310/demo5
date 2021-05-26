package com.lyz.demo5.model.VO;

public class ResultJson {
    private String msg;
    private int code;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultJson ok(Object obj){
        ResultJson resultJson = new ResultJson();
        resultJson.setData(obj);
        resultJson.setCode(ResultCode.SUCCESS.getCode());
        resultJson.setMsg(ResultCode.SUCCESS.getMsg());
        return resultJson;
    }
    public static ResultJson error(ResultCode resultCode){
        ResultJson resultJson = new ResultJson();
        resultJson.setData("");
        resultJson.setCode(resultCode.getCode());
        resultJson.setMsg(resultCode.getMsg());
        return resultJson;
    }
    public static ResultJson error(ResultCode resultCode,Object obj){
        ResultJson resultJson = new ResultJson();
        resultJson.setData(obj);
        resultJson.setCode(resultCode.getCode());
        resultJson.setMsg(resultCode.getMsg());
        return resultJson;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + '\"' +
                ", \"data\":\"" + data + '\"'+
                '}';
    }
}
