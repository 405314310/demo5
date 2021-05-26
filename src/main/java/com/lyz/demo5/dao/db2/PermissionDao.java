package com.lyz.demo5.dao.db2;

import com.lyz.demo5.model.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionDao {
    List<Permission> selectAll();
}
