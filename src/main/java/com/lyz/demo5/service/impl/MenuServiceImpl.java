package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.MenuDao;
import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.Test;
import com.lyz.demo5.model.VO.MenuVO;
import com.lyz.demo5.service.MenuService;
import com.lyz.demo5.utils.RedisUtils;
import com.lyz.demo5.utils.SerializableUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SerializableUtils serializableUtils;

    @Override
    public int add(Menu menu) {
        return menuDao.add(menu);
    }

    @Override
    public int delete(String id) {
        return menuDao.delete(id);
    }

    @Override
    public int update(Menu menu) {
        return menuDao.update(menu);
    }

    @Override
    public List<Menu> selectAll(CustomPage customPage) {
        return menuDao.selectAll(customPage);
    }

    @Override
    public Menu selectByName(String name) {
        return menuDao.selectByName(name);
    }

    @Override
    public Menu selectById(String id) {
        return menuDao.selectById(id);
    }

    @Override
    public MenuVO selectMenuTree() {
        List<Menu> menuList = menuDao.selectMenuTree();
        List<MenuVO> menuVOList = new ArrayList<>();
        for(Menu menu :menuList){
            MenuVO menuVO = new MenuVO();
            menuVO.setId(menu.getId());
            menuVO.setName(menu.getName());
            menuVO.setParentId(menu.getParentId());
            menuVO.setUrl(menu.getUrl());
            menuVO.setMenuVOList(new ArrayList<>());
            menuVOList.add(menuVO);
        }
        for (MenuVO menuVO:menuVOList){
            if(menuVO.getParentId().equals("0")){
                getChildren(menuVO,menuVOList);
                return menuVO;
            }
        }

        return null;
    }

    @Override
    public List<String> selectMenuIdByRoleId(String id) {
        return menuDao.selectMenuIdByRoleId(id);
    }

    private void getChildren(MenuVO menuVO1,List<MenuVO> menuVOList){
        for(MenuVO menuVO: menuVOList){
            if(menuVO.getParentId().equals(menuVO1.getId())){
                menuVO1.getMenuVOList().add(menuVO);
                getChildren(menuVO,menuVOList);
            }
        }
    }
}
