package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

public interface PlatformBankInfoMapper {
    int insert(PlatformBankInfo record);

    PlatformBankInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PlatformBankInfo record);

    int queryForCount(PlatformBankInfoQueryObject qo);

    List<PlatformBankInfo> query(PlatformBankInfoQueryObject qo);

    List<PlatformBankInfo> listAll();
}