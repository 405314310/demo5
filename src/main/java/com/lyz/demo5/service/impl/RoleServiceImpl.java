package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.RoleDao;
import com.lyz.demo5.model.Role;
import com.lyz.demo5.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Override
    public int add(Role role) {
        return roleDao.add(role);
    }

    @Override
    public int delete(String id) {
        return roleDao.delete(id);
    }

    @Override
    public int update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public List<Role> selectAll() {
        return roleDao.selectAll();
    }

    @Override
    public Role selectById(String id) {
        return roleDao.selectById(id);
    }

    @Override
    public Role selectByName(String name) {
        return roleDao.selectByName(name);
    }
}
