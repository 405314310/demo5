package com.lyz.demo5.newSecurity;

import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println("CustomAuthenticationEntryPoint-----------commence:未登录");
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        pw.println(ResultJson.error(ResultCode.PAGE_NOLOGIN).toString());
        pw.flush();
        pw.close();
    }
}
