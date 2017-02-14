package com.xmg.p2p.business.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

/**
 * Created by Panda on 2016/11/6.
 */
public interface IPlatformBankInfoService {
    int insert(PlatformBankInfo record);

    PlatformBankInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PlatformBankInfo cord);

    PageResult query(PlatformBankInfoQueryObject qo);

    /**
     * 银行账户的保存或更新
     * @param pb
     */
    void saveOrUpdate(PlatformBankInfo pb);

    /**
     * 查询出所有的账号
     * @return
     */
    List<PlatformBankInfo> list();
}
