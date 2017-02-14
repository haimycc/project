package com.xmg.p2p.business.service;

/**
 * Created by Panda on 2016/11/8.
 */

import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.MoneyWithDraw;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;
import com.xmg.p2p.business.domain.SystemAccount;

import java.math.BigDecimal;

/**
 * 系统账户服务对象
 */
public interface ISystemAccountService {
    void update(SystemAccount systemAccount);

    /**
     * 初始化系统账户
     */
    void init();


    /**
     * 收取借款手续费,并生成系统账户流水
     * @param borrowChargeFee
     * @param bidRequest
     */
    void chargeBorrowFee(BigDecimal borrowChargeFee, BidRequest bidRequest);

    /**
     * 收取提现成功手续费,并生成系统账户流水
     * @param m
     */
    void chargeWithDrawFee(MoneyWithDraw m);

    /**
     * 系统收取利息管理费
     * @param interestManagerCharge
     * @param psd
     */
    void chargeInterestFee(BigDecimal interestManagerCharge, PaymentScheduleDetail psd);
}
