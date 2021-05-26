package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface DepartmentDao {

    int add(Department department);
    int delete(String id);
    int update(Department department);
    List<Department> selectAll();
    Department selectByName(String name);
    Department selectById(String id);

}
