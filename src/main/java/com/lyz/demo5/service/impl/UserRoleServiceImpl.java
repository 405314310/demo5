package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.UserRoleVODao;
import com.lyz.demo5.model.VO.UserRoleVO;
import com.lyz.demo5.service.UserRoleVOService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleVOService {

    @Resource
    private UserRoleVODao userRoleVODao;

    @Override
    public List<UserRoleVO> selectAll() {
        return userRoleVODao.selectAll();
    }

    @Override
    public UserRoleVO selectById(String id) {
        return userRoleVODao.selectById(id);
    }

    @Override
    public int add(String id,String userId, String roleId) {
        return userRoleVODao.add(id,userId,roleId);
    }

    @Override
    public int delete(String userId, String roleId) {
        return userRoleVODao.delete(userId, roleId);
    }

    @Override
    public UserRoleVO selectByUserRoleId(String userId, String roleId) {
        return userRoleVODao.selectByUserRoleId(userId, roleId);
    }

    @Override
    public int deleteByUserId(String userId) {
        return userRoleVODao.deleteByUserId(userId);
    }
}
