package com.lyz.demo5.service;

import com.lyz.demo5.model.Role;

import java.util.List;

public interface RoleService {
    int add(Role role);
    int delete(String id);
    int update(Role role);
    List<Role> selectAll();
    Role selectById(String id);
    Role selectByName(String name);
}
