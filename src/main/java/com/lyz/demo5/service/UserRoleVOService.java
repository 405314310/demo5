package com.lyz.demo5.service;

import com.lyz.demo5.model.VO.UserRoleVO;

import java.util.List;

public interface UserRoleVOService {
    List<UserRoleVO> selectAll();

    UserRoleVO selectById(String id);

    int add(String id, String userId,String roleId);

    int delete(String userId,String roleId);

    UserRoleVO selectByUserRoleId(String userId,String roleId);

    int deleteByUserId(String userId);
}
