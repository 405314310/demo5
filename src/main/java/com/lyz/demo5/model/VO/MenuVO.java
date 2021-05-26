package com.lyz.demo5.model.VO;

import com.lyz.demo5.model.Menu;

import java.util.List;

public class MenuVO extends Menu {
    private List<MenuVO> menuVOList;

    public List<MenuVO> getMenuVOList() {
        return menuVOList;
    }

    public void setMenuVOList(List<MenuVO> menuVOList) {
        this.menuVOList = menuVOList;
    }
}
