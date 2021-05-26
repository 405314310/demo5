package com.lyz.demo5.model.VO;

import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.Role;

import java.util.List;

public class MenuRoleVO extends Menu {

    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
