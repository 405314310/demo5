package com.lyz.demo5.model.VO;

public enum ResultCode {
    SUCCESS(1,"成功")
    ,
    PAGE_ERROR(2,"参数不正确")
    ,
    SERVER_ERROR(3,"服务器错误")
    ,
    DATABASE_NOTFOUND(4,"数据库没有找到数据")
    ,
    DATABASE_REPEAT(5,"数据库检查到数据重复")
    ,
    DATABASE_ERROR(6,"数据库执行操作失败")
    ,
    PAGE_NOLOGIN(7,"未登录")
    ,
    PAGE_ACCESSDENIED(8,"权限不足不允许访问")
    ,
    PAGE_LOGINERROR(9,"登陆错误")
    ;
    private int code;
    private String msg;

    ResultCode(int code,String msg){
        this.code = code;
        this.msg =msg;
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
