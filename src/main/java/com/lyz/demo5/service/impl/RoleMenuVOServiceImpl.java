package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.RoleMenuVODao;
import com.lyz.demo5.model.VO.RoleMenuVO;
import com.lyz.demo5.service.RoleMenuVOService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleMenuVOServiceImpl implements RoleMenuVOService {

    @Resource
    private RoleMenuVODao roleMenuVODao;

    @Override
    public RoleMenuVO selectById(String id) {
        return roleMenuVODao.selectById(id);
    }

    @Override
    public int add(String id, String roleId, String menuId) {
        return roleMenuVODao.add(id, roleId, menuId);
    }

    @Override
    public int delete(String roleId, String menuId) {
        return roleMenuVODao.delete(roleId,menuId);
    }

    @Override
    public RoleMenuVO selectByRoleMenuId(String roleId, String menuId) {
        return roleMenuVODao.selectByRoleMenuId(roleId,menuId);
    }

    @Override
    public int deleteByRoleId(String roleId) {
        return roleMenuVODao.deleteByRoleId(roleId);
    }
}
