package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.business.domain.UserBankinfo;
import com.xmg.p2p.business.mapper.UserBankinfoMapper;
import com.xmg.p2p.business.service.IUserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Panda on 2016/11/9.
 */
@Service
public class UserBankinfoServiceImpl implements IUserBankinfoService {
    @Autowired
    private UserBankinfoMapper userBankinfoMapper;

    @Autowired
    private IUserInfoService userInfoService;

    public int insert(UserBankinfo record) {
        return userBankinfoMapper.insert(record);
    }

    public UserBankinfo selectByUserId(Long userinfoId) {
        return userBankinfoMapper.selectByUserId(userinfoId);
    }

    public void save(UserBankinfo userBankinfo) {
        //判断用户是否已经绑定过银行卡
        UserInfo current = userInfoService.getCurrent();
        //没有绑定过银行卡
        if (!current.getIsBindBank() && current.getIsRealAuth()) {
            UserBankinfo bankinfo = new UserBankinfo();
            bankinfo.setBankName(userBankinfo.getBankName());
            bankinfo.setAccountNumber(userBankinfo.getAccountNumber());
            bankinfo.setBankForkName(userBankinfo.getBankForkName());
            bankinfo.setAccountName(current.getRealName());
            bankinfo.setUserinfoId(current.getId());
            userBankinfoMapper.insert(bankinfo);
            current.addState(BitStatesUtils.OP_BIND_BANK);
            userInfoService.update(current);
        }
    }
}
