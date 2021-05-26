package com.lyz.demo5.controller;

import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.User;
import com.lyz.demo5.model.VO.MenuVO;
import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @PostMapping("/add")
    public Object add(@RequestBody Menu menu){
        if(menu==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(menu.getName()==null||menu.getParentId()==null||menu.getUrl()==null
                || menu.getName().equals("")||menu.getParentId().equals("")||menu.getUrl().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }

        Menu parentMenu = menuService.selectById(menu.getParentId()); // 检查父级菜单是否存在
        if(parentMenu==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        menu.setId(UUID.randomUUID().toString());
        int result = menuService.add(menu);

        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }

        return ResultJson.ok("");
    }


    @PostMapping("/delete")
    public Object delete(@RequestBody Menu menu){
        if(menu==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(menu.getId()==null||menu.getId().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        Menu menu1 = menuService.selectById(menu.getId()); // 检查菜单是否存在
        if(menu1==null){
            return ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        int result = menuService.delete(menu.getId());
        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }

        return ResultJson.ok("");
    }


    @PostMapping("/update")
    public Object update(@RequestBody Menu menu){

        if(menu==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        if(menu.getId()==null||menu.getName()==null
                || menu.getParentId()==null||menu.getUrl()==null||menu.getId().equals("")
                ||menu.getName().equals("")||menu.getParentId().equals("")||menu.getUrl().equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        Menu parentMenu = menuService.selectById(menu.getParentId());
        if(parentMenu==null){
            ResultJson.error(ResultCode.DATABASE_NOTFOUND);
        }

        int result = menuService.update(menu);
        if(result==0){
            return ResultJson.error(ResultCode.DATABASE_ERROR);
        }

        return ResultJson.ok("");
    }


    @PostMapping("/selectAllByPage")
    public Object selectAllByPage(@RequestBody CustomPage customPage){
        System.out.println(customPage);
        if(customPage==null){
            customPage = new CustomPage();
        }
        if(customPage.getPageRow()==null||customPage.getPageRow()==0){
            customPage.setPageRow(10);
        }
        if(customPage.getPageNum()==null|| customPage.getPageNum()==0){
            customPage.setPageNum(1);
        }
        customPage.setStartNum((customPage.getPageNum()-1)* customPage.getPageRow());


        List<Menu> result = menuService.selectAll(customPage);
        return ResultJson.ok(result);
    }

    @PostMapping("/selectMenuTree")
    public Object selectMenuTree(){
        MenuVO menuVO = menuService.selectMenuTree();
        return ResultJson.ok(menuVO);
    }

}
