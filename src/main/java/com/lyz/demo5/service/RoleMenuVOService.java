package com.lyz.demo5.service;

import com.lyz.demo5.model.VO.RoleMenuVO;

public interface RoleMenuVOService {

    RoleMenuVO selectById(String id);
    int add(String id,String roleId,String menuId);
    int delete(String roleId,String menuId);

    RoleMenuVO selectByRoleMenuId(String roleId,String menuId);

    int deleteByRoleId(String roleId);
}
