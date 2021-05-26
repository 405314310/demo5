package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.UserDao;
import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.User;
import com.lyz.demo5.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public int delete(String id) {
        return userDao.delete(id);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public List<User> selectAll(CustomPage customPage) {
        return userDao.selectAll(customPage);
    }

    @Override
    public User selectByName(String name) {
        return userDao.selectByName(name);
    }

    @Override
    public User selectByNamePwd(User user) {
        return userDao.selectByNamePwd(user);
    }

    @Override
    public User selectById(String id) {
        return userDao.selectById(id);
    }

    @Override
    public int updateUserStatus(String userId, String status) {
        return userDao.updateUserStatus(userId,status);
    }

}
