package com.lyz.demo5.controller;

import com.lyz.demo5.model.VO.ResultCode;
import com.lyz.demo5.model.VO.ResultJson;
import com.lyz.demo5.model.VO.UserMenuVO;
import com.lyz.demo5.service.UserMenuVOService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/userMenu")
public class UserMenuVOController {

    @Resource
    private UserMenuVOService userMenuVOService;

    @PostMapping("/selectMenuByUserId")
    public Object selectMenuByUserId(@RequestBody Map<String,Object> map){
        if(map==null || map.get("userId")==null){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        String userId=map.get("userId").toString();
        if(userId.equals("")){
            return ResultJson.error(ResultCode.PAGE_ERROR);
        }
        UserMenuVO userMenuVO = userMenuVOService.selectMenuByUserId(userId);

        return ResultJson.ok(userMenuVO);
    }
}
