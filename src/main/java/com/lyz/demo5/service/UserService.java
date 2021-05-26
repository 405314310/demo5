package com.lyz.demo5.service;

import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.User;

import java.util.List;

public interface UserService {
    int add(User user);
    int delete(String id);
    int update(User user);
    List<User> selectAll(CustomPage customPage);
    User selectByName(String name);

    User selectByNamePwd(User user);

    User selectById(String id);

    int updateUserStatus(String userId,String status);

}
