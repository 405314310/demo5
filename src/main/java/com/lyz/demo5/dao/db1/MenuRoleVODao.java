package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.VO.MenuRoleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuRoleVODao {

    List<MenuRoleVO> selectAll();

}
