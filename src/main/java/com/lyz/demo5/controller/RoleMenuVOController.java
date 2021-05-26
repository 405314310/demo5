package com.lyz.demo5.controller;

import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.Role;
import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.model.VO.RoleMenuVO;
import com.lyz.demo5.service.MenuService;
import com.lyz.demo5.service.RoleMenuVOService;
import com.lyz.demo5.service.RoleService;
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
@RequestMapping("/roleMenu")
public class RoleMenuVOController {
    @Resource
    private RoleMenuVOService roleMenuVOService;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @PostMapping("/selectById")
    public Object selectById(@RequestBody Map<String,Object> map){
        if(map==null || map.get("roleId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String roleId=map.get("roleId").toString();
        if(roleId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        RoleMenuVO roleMenuVO = roleMenuVOService.selectById(roleId);

        return ResultJson.ok(roleMenuVO);
    }
    @PostMapping("/selectMenuIdByRoleId")
    public  Object selectMenuIdByRoleId(@RequestBody Map<String,Object> map){
        if(map==null || map.get("roleId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String roleId=map.get("roleId").toString();
        if(roleId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        List<String> menuIdList = menuService.selectMenuIdByRoleId(roleId);
        return ResultJson.ok(menuIdList);
    }
    @PostMapping("/addRoleMenus")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public Object addRoleMenus(@RequestBody Map<String,Object> map) {
        if(map.get("roleId")==null||map.get("menuIdList")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String roleId = map.get("roleId").toString();
        List<String> menuIdList = new ArrayList<>();
        menuIdList = (List) map.get("menuIdList");
        if(roleId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        int deleteResult = roleMenuVOService.deleteByRoleId(roleId);
        System.out.println(deleteResult);

        for(String menuId : menuIdList){
            String id = UUID.randomUUID().toString();
            roleMenuVOService.add(id,roleId,menuId);
        }

        return ResultJson.ok("");
    }
    @PostMapping("/addRoleMenu")
    public Object addRoleMenu(@RequestBody Map<String,Object> map){
        if(map==null || map.get("roleId")==null||map.get("menuId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String roleId = map.get("roleId").toString();
        String menuId = map.get("menuId").toString();
        if(roleId.equals("")||menuId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        Role role1 = roleService.selectById(roleId);
        if(role1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        Menu menu1 = menuService.selectById(menuId);
        if(menu1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }



        RoleMenuVO roleMenuVO1 = roleMenuVOService.selectByRoleMenuId(roleId,menuId);
        if(roleMenuVO1!=null){
            return ResultJson.error(ResultCode.DATABASE_REPEAT);
        }

        String id = UUID.randomUUID().toString();
        int result = roleMenuVOService.add(id,roleId,menuId);
        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }
        return ResultJson.ok("");
    }

    @PostMapping("/deleteRoleMenu")
    public Object deleteRoleMenu(@RequestBody Map<String,Object> map){
        if(map.get("roleId")==null||map.get("menuId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String roleId = map.get("roleId").toString();
        String menuId = map.get("menuId").toString();
        if(roleId.equals("")||menuId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        Role role1 = roleService.selectById(roleId);
        if(role1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        Menu menu1 = menuService.selectById(menuId);
        if(menu1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        RoleMenuVO roleMenuVO1 = roleMenuVOService.selectByRoleMenuId(roleId,menuId);
        if(roleMenuVO1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        int result = roleMenuVOService.delete(roleId,menuId);
        if( result == 0 ){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }
        return ResultJson.ok("");

    }
}
