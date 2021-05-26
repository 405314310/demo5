package com.lyz.demo5.model.VO;

import com.lyz.demo5.model.Department;
import com.lyz.demo5.model.User;

import java.util.List;

public class DepartmentUserVO extends Department {
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
