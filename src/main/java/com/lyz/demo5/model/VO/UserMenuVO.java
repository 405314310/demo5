package com.lyz.demo5.model.VO;

import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.User;

import java.util.List;

public class UserMenuVO extends User {

    private List<Menu> menuList;

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
