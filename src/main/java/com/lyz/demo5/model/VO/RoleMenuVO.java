package com.lyz.demo5.model.VO;

import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.Role;

import java.util.List;

public class RoleMenuVO extends Role {

    private List<Menu> menuList;

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
