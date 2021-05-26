package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.VO.RoleMenuVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMenuVODao {


    int add(String id,String roleId,String menuId);
    int delete(String roleId,String menuId);
    RoleMenuVO selectById(String id);
    RoleMenuVO selectByRoleMenuId(String roleId,String menuId);
    int deleteByRoleId(String roleId);
}
