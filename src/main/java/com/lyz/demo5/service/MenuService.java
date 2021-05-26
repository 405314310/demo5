package com.lyz.demo5.service;

import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.VO.MenuVO;

import java.util.List;

public interface MenuService {
    int add(Menu menu);
    int delete(String id);
    int update(Menu menu);
    List<Menu> selectAll(CustomPage customPage);
    Menu selectByName(String name);
    Menu selectById(String id);
    MenuVO selectMenuTree();
    List<String> selectMenuIdByRoleId(String id);
}
