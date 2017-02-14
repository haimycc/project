package com.xmg.p2p.business.service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.business.domain.*;

import java.math.BigDecimal;

/**
 * Created by Panda on 2016/11/7.
 */
public interface IAccountFlowService {
    int insert(AccountFlow accountFlow);

    /**
     * 充值审核通过的方法
     * @param account
     * @param r
     */
    void rechargeFlow(Account account, RechargeOffline r);

    /**
     * 投资完成的方法
     * @param account
     * @param bid
     */
    void bidFlow(Account account, Bid bid);

    /**
     * 投标失败流水
     * @param bidAccount
     * @param bid
     */
    void bidFaildFlow(Account bidAccount, Bid bid);

    /**
     * 成功借款的流水
     * @param borrowAccount
     * @param bidRequest
     */
    void borrowSuccessFlow(Account borrowAccount, BidRequest bidRequest);

    /**
     * 支付借款手续费流水
     * @param borrowAccount
     * @param bidRequest
     * @param borrowChargeFee
     */
    void borrowChargeFlow(Account borrowAccount, BidRequest bidRequest, BigDecimal borrowChargeFee);

    /**
     * 投资成功交易流水
     * @param bidAccount
     * @param bid
     */
    void bidSuccessFlow(Account bidAccount, Bid bid);

    /**
     * 生成提现申请流水
     * @param account
     * @param m
     */
    void withDrawFlow(Account account, MoneyWithDraw m);

    /**
     * 支付提现成功手续费流水
     * @param account
     * @param m
     */
    void withDrawSuccessFlow(Account account, MoneyWithDraw m);

    /**
     * 支付提现成功手续费流水
     * @param account
     * @param m
     */
    void withDrawChargeFlow(Account account, MoneyWithDraw m);

    /**
     * 提现失败流水
     * @param account
     * @param m
     */
    void withDrawFaildFlow(Account account, MoneyWithDraw m);

    /**
     * 还款成功流水
     * @param current
     * @param pay
     */
    void returnMoneyFlow(Account current, PaymentSchedule pay);

    /**
     * 收取还款交易流水
     * @param bidAccount
     * @param psd
     */
    void receiveFlow(Account bidAccount, PaymentScheduleDetail psd);

    void interestChargeFlow(Account bidAccount, BigDecimal interestManagerCharge, PaymentScheduleDetail psd);
}
