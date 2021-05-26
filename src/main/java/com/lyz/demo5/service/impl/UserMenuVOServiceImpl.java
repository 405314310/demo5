package com.lyz.demo5.service.impl;

import com.lyz.demo5.dao.db1.UserMenuVODao;
import com.lyz.demo5.model.VO.UserMenuVO;
import com.lyz.demo5.service.UserMenuVOService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserMenuVOServiceImpl implements UserMenuVOService {

    @Resource
    private UserMenuVODao userMenuVODao;


    @Override
    public UserMenuVO selectMenuByUserId(String userId) {
        return userMenuVODao.selectMenuByUserId(userId);
    }
}
