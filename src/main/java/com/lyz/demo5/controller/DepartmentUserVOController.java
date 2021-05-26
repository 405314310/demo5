package com.lyz.demo5.controller;

import com.lyz.demo5.model.Department;
import com.lyz.demo5.model.Role;
import com.lyz.demo5.model.User;
import com.lyz.demo5.model.VO.DepartmentUserVO;
import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.model.VO.UserRoleVO;
import com.lyz.demo5.service.DepartmentService;
import com.lyz.demo5.service.DepartmentUserVOService;
import com.lyz.demo5.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/departmentUser")
public class DepartmentUserVOController {

    @Resource
    private DepartmentUserVOService departmentUserVOService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private UserService userService;

    @PostMapping("/addDepartmentUser")
    public Object addDepartmentUser(@RequestBody Map<String,Object> map){
        if(map.get("departmentId")==null||map.get("userId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String departmentId = map.get("departmentId").toString();
        String userId = map.get("userId").toString();
        if(departmentId.equals("")||userId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }


        Department department = departmentService.selectById(departmentId);
        if(department==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        User user = userService.selectById(userId);
        if(user==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }


        String resultId = departmentUserVOService.selectIdByDepartmentUserId(departmentId,userId); //查询数据是否存在数据库

        if(resultId!=null){
            return ResultJson.error(ResultCode.DATABASE_REPEAT);
        }


        String id = UUID.randomUUID().toString();

        int result = departmentUserVOService.add(id,departmentId,userId);
        if(result>0){
            return ResultJson.ok("添加成功");

        }
        return ResultJson.error(ResultCode.DATABASE_ERROR);

    }

    @PostMapping("/deleteDepartmentUser")
    public Object deleteDepartmentUser (@RequestBody Map<String,Object> map){
        if(map.get("departmentId")==null||map.get("userId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String departmentId = map.get("departmentId").toString();
        String userId = map.get("userId").toString();
        if(departmentId.equals("")||userId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }


        Department department = departmentService.selectById(departmentId);
        if(department==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        User user = userService.selectById(userId);
        if(user==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }


        String resultId = departmentUserVOService.selectIdByDepartmentUserId(departmentId,userId); //查询数据是否存在数据库

        if(resultId==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        int result = departmentUserVOService.deleteById(resultId);
        if(result>0){
            return ResultJson.ok("删除成功");

        }
        return ResultJson.error(ResultCode.DATABASE_ERROR);

    }

    @PostMapping("/selectByDepartmentId")
    public Object selectByDepartmentId(@RequestBody Map<String,Object> map){
        if(map.get("departmentId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        String departmentId = map.get("departmentId").toString();

        DepartmentUserVO departmentUserVO = departmentUserVOService.selectByDepartmentId(departmentId);
        if(departmentUserVO==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        return ResultJson.ok(departmentUserVO);
    }

}
