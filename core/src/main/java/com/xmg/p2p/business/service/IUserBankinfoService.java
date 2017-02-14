package com.xmg.p2p.business.service;

import com.xmg.p2p.business.domain.UserBankinfo;

/**
 * Created by Panda on 2016/11/9.
 */
public interface IUserBankinfoService {
    int insert(UserBankinfo record);

    UserBankinfo selectByUserId(Long userinfoId);

    /**
     * 绑定银行卡
     * @param userBankinfo
     */
    void save(UserBankinfo userBankinfo);
}
