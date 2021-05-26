package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.DepartmentUserVODao;
import com.lyz.demo5.model.VO.DepartmentUserVO;
import com.lyz.demo5.service.DepartmentUserVOService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentUserVOServiceImpl implements DepartmentUserVOService {

    @Resource
    private DepartmentUserVODao departmentUserVODao;


    @Override
    public int add(String id, String departmentId, String userId) {
        return departmentUserVODao.add(id,departmentId,userId);
    }

    @Override
    public int delete(String departmentId, String userId) {
        return departmentUserVODao.delete(departmentId,userId);
    }

    @Override
    public int deleteById(String id) {
        return departmentUserVODao.deleteById(id);
    }

    @Override
    public DepartmentUserVO selectByDepartmentId(String id) {
        return departmentUserVODao.selectByDepartmentId(id);
    }

    @Override
    public DepartmentUserVO selectByDepartmentUserId(String departmentId, String userId) {
        return departmentUserVODao.selectByDepartmentUserId(departmentId,userId);
    }

    @Override
    public String selectIdByDepartmentUserId(String departmentId, String userId) {
        return departmentUserVODao.selectIdByDepartmentUserId(departmentId,userId);
    }
}
