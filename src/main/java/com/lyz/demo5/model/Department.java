package com.lyz.demo5.model;

import com.alibaba.excel.annotation.ExcelProperty;

public class Department {

    @ExcelProperty("部门Id")
    private String id;
    @ExcelProperty("部门名称")
    private String name;

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
}
