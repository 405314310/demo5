package com.lyz.demo5.service;

import com.lyz.demo5.model.Department;

import java.util.List;

public interface DepartmentService {

    int add(Department department);
    int delete(String id);
    int update(Department department);
    List<Department> selectAll();

    Department selectByName(String name);
    Department selectById(String id);
}
