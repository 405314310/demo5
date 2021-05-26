package com.lyz.demo5.model.VO;

import com.lyz.demo5.model.Role;
import com.lyz.demo5.model.User;

import java.util.List;

public class UserRoleVO extends User {
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
