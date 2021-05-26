package com.lyz.demo5.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户实体类
 */

public class User {

    private String id;
    private String name;
    private String pwd;
    private Date createTime;
    private String email;

    @Getter
    @Setter
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
