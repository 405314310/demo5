package com.lyz.demo5.controller;

import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.User;
import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/add")
    public Object add(@RequestBody User user)  {
        if(user==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(user.getName()==null||user.getPwd()==null||user.getName().equals("")||user.getPwd().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        User user1 = userService.selectByName(user.getName()); // 查询用户名是否存在
        if(user1!=null){
            return ResultJson.error(ResultCode.DATABASE_REPEAT);
        }

        user.setId(UUID.randomUUID().toString());//生成uuid随机数

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

        int result = userService.add(user);

        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }else{
            return ResultJson.ok("");
        }

    }


    @PostMapping("/login")
    public Object login(@RequestBody User user){
        System.out.println("/login----------------------------");
        if(user==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(user.getName()==null||user.getPwd()==null||user.getName().equals("")||user.getPwd().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }



        User userResult = userService.selectByName(user.getName());

        if(userResult==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }


        boolean isOk = bCryptPasswordEncoder.matches(user.getPwd(),userResult.getPwd());
        System.out.println(isOk);

        if(isOk){
            return ResultJson.ok("登录成功");

        }else{
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

    }



    @PostMapping("/delete")
    public Object delete(@RequestBody User user){
        if(user==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(user.getId()==null||user.getId().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        User user1 = userService.selectById(user.getId());
        if(user1 ==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        int result = userService.delete(user.getId());
        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }
        return ResultJson.ok("");
    }


    @PostMapping("/update")
    public Object update(@RequestBody User user){
        if(user==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(user.getId()==null||user.getId().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        User user1 = userService.selectById(user.getId());
        if(user1 ==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        if(user.getName()!=null&&!user.getName().equals("")){
            String pwd = user.getPwd();

            pwd = bCryptPasswordEncoder.encode(pwd);

            user.setPwd(pwd);
        }
        int result = userService.update(user);
        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }
        return ResultJson.ok("");
    }


    @PostMapping("/selectAllByPage")
    public Object selectAllByPage(@RequestBody CustomPage customPage){
        if(customPage==null){
            customPage = new CustomPage();
        }
        if(customPage.getPageRow()==null||customPage.getPageRow()==0){
            customPage.setPageRow(10);
        }
        if(customPage.getPageNum()==null|| customPage.getPageNum()==0){
            customPage.setPageNum(1);
        }
        customPage.setStartNum((customPage.getPageNum()-1)* customPage.getPageRow());//设置 limit 开始行

        List<User> userList = userService.selectAll(customPage);


        return ResultJson.ok(userList);
    }

    @PostMapping("/updateUserStatus")
    public Object updateUserStatus(@RequestBody Map<String,Object> map){
        if(map.get("userId")==null||map.get("status")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String userId = map.get("userId").toString();
        String status = map.get("status").toString();

        int result = userService.updateUserStatus(userId,status);
        if(result>0){
            return ResultJson.ok("user状态更改成功");
        }
        return ResultJson.error(ResultCode.SERVER_ERROR,"user状态更改失败");
    }

}
