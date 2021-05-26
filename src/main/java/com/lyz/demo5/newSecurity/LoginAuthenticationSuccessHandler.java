package com.lyz.demo5.newSecurity;

import com.lyz.demo5.utils.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtTokenUtils jwtTokenUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("LoginAuthenticationSuccessHandler------------------onAuthenticationSuccess:登录成功生成TOKEN  存入redis数据库");
        String name = authentication.getName();
        String tokenStr = jwtTokenUtils.generateTokenByUserName(name);
        System.out.println(authentication.getName());

    }
}
