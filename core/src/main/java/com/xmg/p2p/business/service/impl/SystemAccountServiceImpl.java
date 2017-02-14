package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.*;
import com.xmg.p2p.business.mapper.SystemAccountFlowMapper;
import com.xmg.p2p.business.mapper.SystemAccountMapper;
import com.xmg.p2p.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Panda on 2016/11/8.
 */
@Service
public class SystemAccountServiceImpl implements ISystemAccountService {
    @Autowired
    private SystemAccountMapper systemAccountMapper;
    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;

    public void update(SystemAccount systemAccount) {
        int ret = systemAccountMapper.updateByPrimaryKey(systemAccount);
        if (ret <= 0) {
            throw new RuntimeException("乐观锁失败");
        }
    }

    @Override
    public void init() {
        SystemAccount systemAccount = systemAccountMapper.selectCurrent();
        if (systemAccount == null) {
            SystemAccount current = new SystemAccount();
            systemAccountMapper.insert(current);
        }
    }


    public void chargeBorrowFee(BigDecimal borrowChargeFee, BidRequest bidRequest) {
        SystemAccount current = systemAccountMapper.selectCurrent();
        //可用余额增加
        current.setUsableAmount(current.getUsableAmount().add(borrowChargeFee));
        //生成流水
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setActionType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE);
        flow.setAmount(borrowChargeFee);
        flow.setFreezedAmount(current.getFreezedAmount());
        flow.setNote("借款" + bidRequest.getTitle() + "成功,收取借款手续费" + borrowChargeFee);
        flow.setTradeTime(new Date());
        flow.setUserableAmount(current.getUsableAmount());
        systemAccountFlowMapper.insert(flow);
        update(current);
    }


    public void chargeWithDrawFee(MoneyWithDraw m) {
        SystemAccount current = systemAccountMapper.selectCurrent();
        //可用余额增加
        current.setUsableAmount(current.getUsableAmount().add(BidConst.MONEY_WITHDRAW_CHARGEFEE));
        //生成流水
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setActionType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);
        flow.setAmount(BidConst.MONEY_WITHDRAW_CHARGEFEE);
        flow.setFreezedAmount(current.getFreezedAmount());
        flow.setNote(m.getApplier().getUsername() + "提现成功"+m.getAmount()+"收取手续费" + m.getChargeFee());
        flow.setTradeTime(new Date());
        flow.setUserableAmount(current.getUsableAmount());
        systemAccountFlowMapper.insert(flow);
        update(current);
    }

    public void chargeInterestFee(BigDecimal interestManagerCharge, PaymentScheduleDetail psd) {
        SystemAccount current = systemAccountMapper.selectCurrent();
        //可用余额增加
        current.setUsableAmount(current.getUsableAmount().add(interestManagerCharge));
        //生成流水
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setActionType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE);
        flow.setAmount(interestManagerCharge);
        flow.setFreezedAmount(current.getFreezedAmount());
        flow.setNote("收取利息管理费" + interestManagerCharge);
        flow.setTradeTime(new Date());
        flow.setUserableAmount(current.getUsableAmount());
        systemAccountFlowMapper.insert(flow);
        update(current);
    }
}
