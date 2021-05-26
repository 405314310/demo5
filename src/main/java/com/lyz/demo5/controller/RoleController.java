package com.lyz.demo5.controller;

import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.Role;
import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService RoleService;


    @PostMapping("/add")
    public Object add(@RequestBody Role role){
        if(role==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(role.getName()==null||role.getName().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        Role role1 = RoleService.selectByName(role.getName());
        if(role1!=null){
            return ResultJson.error(ResultCode.DATABASE_REPEAT);
        }
        role.setId(UUID.randomUUID().toString());
        int result = RoleService.add(role);
        if(result>0){
            return ResultJson.ok("");

        }
        return ResultJson.error(ResultCode.DATABASE_ERROR);

    }

    @PostMapping("/delete")
    public Object delete(@RequestBody Role role){
        if(role==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(role.getId()==null||role.getId().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        Role role1 = RoleService.selectById(role.getId());
        if(role1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        int result = RoleService.delete(role.getId());
        if(result>0){
            return ResultJson.ok("");

        }
        return ResultJson.error(ResultCode.DATABASE_ERROR);
    }

    @PostMapping("/update")
    public Object update(@RequestBody Role role){
        if(role==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(role.getId()==null||role.getName()==null||role.getId().equals("")||role.getName().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        Role role1 = RoleService.selectById(role.getId());
        if(role1!=null){
            return ResultJson.error(ResultCode.DATABASE_REPEAT);
        }
        int result = RoleService.update(role);
        if(result>0){
            return ResultJson.ok("");

        }
        return ResultJson.error(ResultCode.DATABASE_ERROR);

    }

    @PostMapping("/selectAll")
    public Object selectAll(@RequestBody CustomPage customPage){
        System.out.println("roleSelectAll");
        return ResultJson.ok(RoleService.selectAll());
    }


}
