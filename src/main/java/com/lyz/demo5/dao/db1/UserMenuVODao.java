package com.lyz.demo5.dao.db1;

import com.lyz.demo5.model.VO.UserMenuVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMenuVODao {

    UserMenuVO selectMenuByUserId(String userId);

}
