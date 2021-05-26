package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.CustomPage;
import com.lyz.demo5.model.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDao {
    int add(Menu menu);
    int delete(String id);
    int update(Menu menu);
    List<Menu> selectAll(CustomPage customPage);
    Menu selectById(String id);
    Menu selectByName(String name);
    List<Menu> selectMenuTree();
    List<String> selectMenuIdByRoleId(String id);
}
