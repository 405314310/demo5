package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    int add(User user);
    int delete(String id);
    int update(User user);
    List<User> selectAll(CustomPage customPage);
    User selectByName(String name);
    User selectByNamePwd(User user);
    User selectById(String id);
    User selectByNameEmail(User user);


    int updateUserStatus(String userId,String status);
}
