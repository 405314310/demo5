package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.MenuRoleVODao;
import com.lyz.demo5.model.VO.MenuRoleVO;
import com.lyz.demo5.service.MenuRoleVOService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuRoleVOServiceImpl implements MenuRoleVOService {

    @Resource
    private MenuRoleVODao menuRoleVODao;

    @Override
    public List<MenuRoleVO> selectAll() {
        return menuRoleVODao.selectAll();
    }
}
