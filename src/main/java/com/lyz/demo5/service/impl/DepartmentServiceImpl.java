package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.DepartmentDao;
import com.lyz.demo5.model.Department;
import com.lyz.demo5.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentDao departmentDao;


    @Override
    public int add(Department department) {
        return departmentDao.add(department);
    }

    @Override
    public int delete(String id) {
        return departmentDao.delete(id);
    }

    @Override
    public int update(Department department) {
        return departmentDao.update(department);
    }

    @Override
    public List<Department> selectAll() {
        return departmentDao.selectAll();
    }

    @Override
    public Department selectByName(String name) {
        return departmentDao.selectByName(name);
    }

    @Override
    public Department selectById(String id) {
        return departmentDao.selectById(id);
    }
}
