package com.lyz.demo5.service;

import com.lyz.demo5.model.VO.UserMenuVO;

public interface UserMenuVOService {
    UserMenuVO selectMenuByUserId(String userId);
}
