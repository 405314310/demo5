package com.lyz.demo5.newSecurity;

import com.lyz.demo5.model.JwtUser;
import com.lyz.demo5.utils.JwtTokenUtils;
import com.lyz.demo5.utils.RedisUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Resource
    private JwtTokenUtils jwtTokenUtils;
    @Resource
    private RedisUtils redisUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if("OPTIONS".equals(request.getMethod().toUpperCase())){
            System.out.println(request.getMethod());
            doFilter(request,response,filterChain);
        }

        System.out.println("JwtTokenFilter---doFilterInternal");
        System.out.println("getAuthentication=="+SecurityContextHolder.getContext().getAuthentication());

        String authToken = request.getParameter("token");
        String authHeader = request.getHeader(jwtTokenUtils.tokenHeader);
        System.out.println(authHeader);
        if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(jwtTokenUtils.tokenPrefix)) {
            //如果header中存在token，则覆盖掉url中的token
            authToken = authHeader.substring(jwtTokenUtils.tokenPrefix.length()); // "Bearer "之后的内容
        }
        if(authToken!=null&&!authToken.equals("")){
            boolean tokenExpired = jwtTokenUtils.isTokenExpired(authToken);
            if(tokenExpired){
                System.out.println("token过期  isTokenExpired返回值:"+tokenExpired);
            }else{
                System.out.println("token未过期  isTokenExpired返回值:"+tokenExpired);
            }

            String userName = jwtTokenUtils.getUsernameFromToken(authToken);//解析token获得用户名

            System.out.println("token解析出的用户名："+userName);

            if(userName!=null&& SecurityContextHolder.getContext().getAuthentication() == null){
                System.out.println("测试解析token"+authToken);
                String redisToken = redisUtils.get(userName);
                if(authToken.equals(redisToken)&& jwtTokenUtils.validateToken(authToken,userName)){

                    System.out.println("用户名:"+userName+"token有效");

                    String rolestr = jwtTokenUtils.getRolesFromToken(authToken);//从token中获取roles

                    Collection<GrantedAuthority> collection = new ArrayList<>();

                    if(rolestr!=null&&!rolestr.equals("")){
                        String[] strings = rolestr.split(",");//拆分roles
                        if(strings!=null&&strings.length>0) {
                            for (int i = 0; i < strings.length; i++) {
                                if (!strings[i].equals("")) {
                                    collection.add(new SimpleGrantedAuthority(strings[i]));
                                    System.out.println("token解析出的role："+strings[i]);
                                }
                            }
                        }
                    }


                    JwtUser jwtUser = new JwtUser(userName,"",collection);
                    //从令牌中获取userId
                    String userId = jwtTokenUtils.getUserIdFromToken(authToken);
                    jwtUser.setId(userId);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                            (jwtUser, null, jwtUser.getAuthorities());

                    authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));

                    //设置用户登录状态
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }



        doFilter(request,response,filterChain);




    }
}
