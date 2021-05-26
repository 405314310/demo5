package com.lyz.demo5.model;

import java.io.Serializable;

/**
 * 菜单实体类
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = -3382340132735674425L;

    private String id;
    private String name;
    private String parentId;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
