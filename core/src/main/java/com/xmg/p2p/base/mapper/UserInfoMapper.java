package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.UserInfo;

public interface UserInfoMapper {

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserInfo record);
}