package com.lyz.demo5.controller;

import com.lyz.demo5.model.JwtUser;
import com.lyz.demo5.model.User;
import com.lyz.demo5.model.VO.MenuVO;
import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.service.AuthService;
import com.lyz.demo5.service.UserRoleVOService;
import com.lyz.demo5.service.UserService;
import com.lyz.demo5.utils.JwtTokenUtils;
import com.lyz.demo5.utils.VCodeUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;
    @Resource
    private UserService userService;
    @Resource
    private UserRoleVOService userRoleVOService;
    @Resource
    private VCodeUtils vCodeUtils;
    @Resource
    private JwtTokenUtils jwtTokenUtils;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Object register(@RequestBody User user){
        if(user==null){
            return ResultJson.error(ResultCode.PAGE_ERROR,"对象能为空");
        }
        if(user.getName()==null||user.getPwd()==null||user.getEmail()==null||user.getName().equals("")||user.getPwd().equals("")||user.getEmail().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR,"参数不能为空");
        }
        String emailStr = user.getEmail();
        boolean checkResult = vCodeUtils.checkEmail(emailStr);//验证邮箱格式
        if(!checkResult){
            return ResultJson.error(ResultCode.PAGE_ERROR,"邮箱格式有误");
        }

        User user1 = authService.selectByName(user.getName()); // 查询用户名是否存在
        if(user1!=null){
            return ResultJson.error(ResultCode.DATABASE_REPEAT,"用户名"+user.getName()+"已存在");
        }

        /**
         * 生成uuid随机数
         */
        user.setId(UUID.randomUUID().toString());

        /**
         * 加密密码
         */
        String pwd = user.getPwd();

        pwd = bCryptPasswordEncoder.encode(pwd);

        user.setPwd(pwd);


        /**
         * 设置生成时间
         */
        user.setCreateTime(new Date());

        int result = authService.add(user);

        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }else{
            return ResultJson.ok("注册成功");
        }

    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody User user){
        System.out.println("controller ----------login----------------------------");
        System.out.println("User:"+user);
        if(user==null){
            return ResultJson.error(ResultCode.PAGE_ERROR,"用户名或密码为空");
        }
        System.out.println("UserName:"+user.getName());
        if(user.getName()==null||user.getPwd()==null||user.getName().equals("")||user.getPwd().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR,"用户名或密码为空");
        }
        String str = "";

        try {
            str = authService.login(user.getName(),user.getPwd());
        } catch (Exception e) {
            return ResultJson.error(ResultCode.PAGE_LOGINERROR,e.getMessage());
        }

        return ResultJson.ok(str);
    }

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/getUserInfo")
    public Object getUserInfo(String token){
        System.out.println(token);
        String name = jwtTokenUtils.getUsernameFromToken(token);
        User user = new User();
        user.setName(name);
        return ResultJson.ok(user);
    }

    /**
     * 修改密码
     * @param map
     * @return
     */
    @PostMapping("/updatePwd")
    public Object updatePwd(@RequestBody Map<String,Object> map){
        if(map.get("pwd")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String pwd =map.get("pwd").toString();
        //这部分不用判断，因为在security里面有做未登录拦截
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null){
            return ResultJson.error(ResultCode.PAGE_NOLOGIN);
        }
        if(authentication.getPrincipal()==null){
            return ResultJson.error(ResultCode.PAGE_NOLOGIN);
        }

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        if(jwtUser.getId()==null){
            return ResultJson.error(ResultCode.PAGE_NOLOGIN);
        }

        /**
         * 加密密码
         */
        pwd = bCryptPasswordEncoder.encode(pwd);

        int result = authService.updatePwdById(jwtUser.getId(),pwd);
        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }
        return ResultJson.ok("更改密码成功");
    }


    /**
     * 忘记密码
     * @return
     */
    @PostMapping("/forgotPwd")
    public Object forgotPwd(@RequestBody Map<String,Object> map){
        if(map.get("userName")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        String userName =map.get("userName").toString();
        if(map.get("email")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        String email =map.get("email").toString();

        String forgotType = "email";

        String result = "";

        //这里“”（“email”），代表了是字符串不需要toString()去转类型
        if(forgotType.equals("email".toString())){
            User user = new User();
            user.setName(userName);
            user.setEmail(email);
            User resultUser = authService.selectByNameEmail(user);//检查用户名和邮箱是否匹配
            if(resultUser==null){
                return ResultJson.error(ResultCode.DATABASE_NOTFOUND,"用户名和邮箱不匹配");
            }
            result = authService.forgotPwdByEmail(user.getName(),user.getEmail());

        }
        //你忘记密码的流程怎么设计的耶


        return result;
    }

    /**
     * 重置密码
     * @return
     */
    @PostMapping("/resetPwd")
    public Object resetPwd(@RequestBody Map<String,Object> map){
        if(map.get("userName")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        String userName =map.get("userName").toString();
        if(map.get("email")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        String email =map.get("email").toString();
        if(map.get("userPwd")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        String userPwd =map.get("userPwd").toString();
        if(map.get("code")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        String code =map.get("code").toString();

        String forgotType = "email";

        String result = "";
        userPwd = bCryptPasswordEncoder.encode(userPwd);

        if(forgotType.equals("email".toString())){



            result = authService.resetPwd(userName,email,userPwd,code);
        }
        return result;
    }

    /**
     * 用户注销
     * @return
     */
    @PostMapping("/logout")
    public Object logout(){
        if( authService.logout()>0){
            return ResultJson.ok("注销成功");
        }
        return ResultJson.error(ResultCode.SERVER_ERROR,"注销失败");
    }

    /**
     * 查询当前用户下菜单
     * @return
     */
    @PostMapping("/selectMenu")
    public Object selectMenu(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null){
            return ResultJson.error(ResultCode.PAGE_NOLOGIN);
        }
        if(authentication.getPrincipal()==null){
            return ResultJson.error(ResultCode.PAGE_NOLOGIN);
        }

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        if(jwtUser.getId()==null){
            return ResultJson.error(ResultCode.PAGE_NOLOGIN);
        }


        MenuVO menuVO = authService.selectMenuByUserId(jwtUser.getId());

        return ResultJson.ok(menuVO);
    }






    /**
     * 注册用户admin 并添加admin权限
     * @return
     */
    @PostMapping("/test")
    public Object test(){
        User user = new User();
        user.setId("10086");
        user.setName("admin");
        user.setPwd(bCryptPasswordEncoder.encode("123123"));
        user.setEmail("405314310@qq.com");
        user.setCreateTime(new Date());


        int result = 0;
        try {
            result = authService.add(user);
        }catch (Exception e){
            System.out.println(e.toString());
        }


        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }else{

            userRoleVOService.add(UUID.randomUUID().toString(),"10086","5");
            return ResultJson.ok("注册管理员成功");
        }
    }


    @PostMapping("/test1")
    @PreAuthorize("hasAnyRole('ROLE_lyzrole','ROLE_admin')")
    public Object test1()  {
        System.out.println("control test:"+ SecurityContextHolder.getContext().getAuthentication());
        return "测试";
    }




}
