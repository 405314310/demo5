package com.lyz.demo5.service;

import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.User;
import com.lyz.demo5.model.VO.MenuVO;

import java.util.List;

public interface AuthService {
    String login(String name,String pwd) throws Exception;
    int updatePwdById(String id,String pwd);
    long logout();

    User selectByName(String name);
    int add(User user);
    User selectByNameEmail(User user);

    String forgotPwdByEmail(String name,String email);
    String resetPwd(String name,String email,String pwd,String code);

    MenuVO selectMenuByUserId(String userId);

    List<MenuVO> testbu ();
}
