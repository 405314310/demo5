package com.lyz.demo5.controller;

import com.lyz.demo5.model.Role;
import com.lyz.demo5.model.User;
import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.model.VO.UserRoleVO;
import com.lyz.demo5.service.RoleService;
import com.lyz.demo5.service.UserRoleVOService;
import com.lyz.demo5.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/userRole")
public class UserRoleVOController {
    @Resource
    private UserRoleVOService userRoleVOService;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @PostMapping("/selectAll")
    public Object selectAll(){

        return ResultJson.ok(userRoleVOService.selectAll());
    }
    @PostMapping("/selectById")
    public Object selectById(@RequestBody Map<String,Object> map){
        if(map.get("userId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String userId=map.get("userId").toString();

        UserRoleVO userRoleVO = userRoleVOService.selectById(userId);
        if(userRoleVO==null){
            //return ResultJson.error(ResultCode.DATABASE_NOTFOUND,"用户不存在或者用户暂无角色");
            return ResultJson.ok("");
        }
        return ResultJson.ok(userRoleVO);
    }

    @PostMapping("/addUserRole")
    public Object addUserRole(@RequestBody Map<String,Object> map){
        if(map.get("userId")==null||map.get("roleId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String userId = map.get("userId").toString();
        String roleId = map.get("roleId").toString();
        if(userId.equals("")||roleId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }


        User user1 = userService.selectById(userId);
        if(user1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        Role role1 = roleService.selectById(roleId);
        if(role1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        UserRoleVO userRoleVO1 = userRoleVOService.selectByUserRoleId(userId,roleId);
        if(userRoleVO1!=null){
            return ResultJson.error(ResultCode.DATABASE_REPEAT);
        }


        String id = UUID.randomUUID().toString();

        int result = userRoleVOService.add(id,userId,roleId);
        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }
        return ResultJson.ok("");
    }

    @PostMapping("/deleteUserRole")
    public Object deleteUserRole(@RequestBody Map<String,Object> map){
        if(map.get("userId")==null||map.get("roleId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String userId = map.get("userId").toString();
        String roleId = map.get("roleId").toString();
        if(userId.equals("")||roleId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        User user1 = userService.selectById(userId);
        if(user1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        Role role1 = roleService.selectById(roleId);
        if(role1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        UserRoleVO userRoleVO1 = userRoleVOService.selectByUserRoleId(userId,roleId);
        if(userRoleVO1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        int result = userRoleVOService.delete(userId,roleId);
        if( result == 0 ){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }
        return ResultJson.ok("");

    }


    @PostMapping("/addUserRoles")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public Object addUserRoles(@RequestBody Map<String,Object> map) {
        if(map.get("userId")==null||map.get("roleIdList")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String userId = map.get("userId").toString();
        List<String> roleIdList = new ArrayList<>();
        roleIdList = (List) map.get("roleIdList");
        if(userId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        int deleteResult = userRoleVOService.deleteByUserId(userId);
        System.out.println(deleteResult);

        for(String roleId : roleIdList){
            String id = UUID.randomUUID().toString();
            userRoleVOService.add(id,userId,roleId);
        }

        return ResultJson.ok("");
    }

}
