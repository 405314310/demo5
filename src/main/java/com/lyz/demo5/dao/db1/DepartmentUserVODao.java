package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.VO.DepartmentUserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentUserVODao {


    int add(String id, String departmentId,String userId);

    int delete(String departmentId,String userId);

    int deleteById(String id);

    DepartmentUserVO selectByDepartmentId(String id);

    DepartmentUserVO selectByDepartmentUserId(String departmentId,String userId);

    String selectIdByDepartmentUserId(String departmentId,String userId);
}
