package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.VO.MenuVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuVODao {
    List<MenuVO> selectChildrenById(String id);
    List<MenuVO> selectParentByParentId(String parentId);
}
