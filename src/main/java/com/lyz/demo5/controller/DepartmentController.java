package com.lyz.demo5.controller;

import com.lyz.demo5.model.Department;
import com.lyz.demo5.model.Role;
import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.service.DepartmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @PostMapping("/add")
    public Object add(@RequestBody Department department){
        if(department==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(department.getName()==null||department.getName().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        Department department1 = departmentService.selectByName(department.getName()); //验证部门名字是否重复
        if(department1!=null){
            return ResultJson.error(ResultCode.DATABASE_REPEAT);
        }
        department.setId(UUID.randomUUID().toString());
        int result = departmentService.add(department);
        if(result>0){
            return ResultJson.ok(ResultCode.SUCCESS);
        }
        return ResultJson.error(ResultCode.DATABASE_ERROR);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody Department department){
        if(department==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(department.getId()==null||department.getId().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        Department department1 = departmentService.selectById(department.getId());
        if(department1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        int result = departmentService.delete(department.getId());
        if(result>0){
            return ResultJson.ok(ResultCode.SUCCESS);
        }


        return ResultJson.error(ResultCode.DATABASE_ERROR);

    }

    @PostMapping("/update")
    public Object update(@RequestBody Department department){
        if(department==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(department.getId()==null||department.getName()==null||department.getId().equals("")||department.getName().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        Department department1 = departmentService.selectById(department.getId());
        if(department1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }
        int result = departmentService.update(department);
        if(result>0){
            return ResultJson.ok(ResultCode.SUCCESS);
        }


        return ResultJson.error(ResultCode.DATABASE_ERROR);
    }

    @PostMapping("/selectAll")
    public Object selectAll(){

        return ResultJson.ok(departmentService.selectAll());
    }
}
