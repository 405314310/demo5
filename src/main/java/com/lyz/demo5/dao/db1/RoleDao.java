package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao {
    int add(Role role);
    int delete(String id);
    int update(Role role);
    List<Role> selectAll();
    Role selectById(String id);
    Role selectByName(String name);
}
