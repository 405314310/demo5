package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.VO.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleVODao {
    List<UserRoleVO> selectAll();
    UserRoleVO selectById(String id);
    int add(String id,String userId,String roleId);
    int delete(String userId,String roleId);

    UserRoleVO selectByUserRoleId(String userId,String roleId);
    int deleteByUserId(String userId);
}
