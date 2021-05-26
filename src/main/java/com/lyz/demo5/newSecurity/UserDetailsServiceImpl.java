package com.lyz.demo5.newSecurity;

import com.lyz.demo5.model.JwtUser;
import com.lyz.demo5.model.Role;
import com.lyz.demo5.model.User;
import com.lyz.demo5.model.VO.UserRoleVO;
import com.lyz.demo5.service.UserRoleVOService;
import com.lyz.demo5.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleVOService userRoleVOService;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl-----------------------loadUserByUsername");
        User user =  userService.selectByName(s);
        if(user == null ){
            System.out.println("user==null");
            throw new UsernameNotFoundException("用户名不存在");
        }

        UserRoleVO userRoleVO = userRoleVOService.selectById(user.getId());

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if(userRoleVO!=null&&userRoleVO.getRoleList()!=null){
            for(Role role : userRoleVO.getRoleList()){
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            }
        }

        JwtUser jwtUser = new JwtUser(user.getName(),user.getPwd(), authorities);
        jwtUser.setId(user.getId());
        jwtUser.setEnabled(user.getStatus().equals("1"));
        System.out.println(jwtUser.getUsername()+"--------------"+jwtUser.getPassword());

        return jwtUser;
    }
}
