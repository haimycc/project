package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Logininfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LogininfoMapper {

    int insert(Logininfo record);

    Logininfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Logininfo record);

    /**
     * 注册
     * @param username
     * @return
     */
    int countByUsername(String username);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Logininfo login(@Param("username") String username, @Param("password")String password, @Param("userType")int userType);

    int countByuserType(int roleManager);

    List<Map<String,Object>> autoComplate(String keyword);
}