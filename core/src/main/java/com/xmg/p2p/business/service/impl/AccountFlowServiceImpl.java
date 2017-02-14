package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.*;
import com.xmg.p2p.business.mapper.AccountFlowMapper;
import com.xmg.p2p.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Panda on 2016/11/7.
 */
@Service
public class AccountFlowServiceImpl implements IAccountFlowService {
    @Autowired
    private AccountFlowMapper accountFlowMapper;

    public void rechargeFlow(Account account, RechargeOffline r) {
        AccountFlow af = createBaseFlow(account);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE);
        af.setAmount(r.getAmount());
        af.setNote("线下充值成功,金额为" + r.getAmount());
        accountFlowMapper.insert(af);
    }

    public int insert(AccountFlow accountFlow) {
        return accountFlowMapper.insert(accountFlow);
    }

    public void bidFlow(Account account, Bid bid) {
        AccountFlow af = createBaseFlow(account);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_BID_FREEZED);
        af.setAmount(bid.getAvailableAmount());
        af.setNote("投标" + bid.getBidRequestTitle() + bid.getAvailableAmount() + "元,生成冻结流水");
        accountFlowMapper.insert(af);
    }

    public void bidFaildFlow(Account bidAccount, Bid bid) {
        AccountFlow af = createBaseFlow(bidAccount);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
        af.setAmount(bid.getAvailableAmount());
        af.setNote("投标" + bid.getBidRequestTitle() + "投资失败,退回冻结金额" + bid.getAvailableAmount());
        accountFlowMapper.insert(af);
    }


    private AccountFlow createBaseFlow(Account account) {
        AccountFlow af = new AccountFlow();
        af.setAccountId(account.getId());
        af.setFreezedAmount(account.getFreezedAmount());
        af.setTradeTime(new Date());
        af.setUserableAmount(account.getUsableAmount());
        return af;
    }

    public void borrowSuccessFlow(Account borrowAccount, BidRequest bidRequest) {
        AccountFlow af = createBaseFlow(borrowAccount);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL);
        af.setAmount(bidRequest.getBidRequestAmount());
        af.setNote("投标" + bidRequest.getTitle() + "成功,收到借款金额" + bidRequest.getBidRequestAmount());
        accountFlowMapper.insert(af);
    }

    public void borrowChargeFlow(Account borrowAccount, BidRequest bidRequest, BigDecimal borrowChargeFee) {
        AccountFlow af = createBaseFlow(borrowAccount);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_CHARGE);
        af.setAmount(borrowChargeFee);
        af.setNote("借款" + bidRequest.getTitle() + "成功,支付接口手续费" + borrowChargeFee);
        accountFlowMapper.insert(af);
    }

    public void bidSuccessFlow(Account bidAccount, Bid bid) {
        AccountFlow af = createBaseFlow(bidAccount);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL);
        af.setAmount(bid.getAvailableAmount());
        af.setNote("投资" + bid.getBidRequestTitle() + "成功,扣款" + bid.getAvailableAmount());
        accountFlowMapper.insert(af);
    }

    @Override
    public void withDrawFlow(Account account, MoneyWithDraw m) {
        AccountFlow af = createBaseFlow(account);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_WITHDRAW_FREEZED);
        af.setAmount(m.getAmount());
        af.setNote("提现冻结金额" + m.getAmount());
        accountFlowMapper.insert(af);
    }

    @Override
    public void withDrawSuccessFlow(Account account, MoneyWithDraw m) {
        AccountFlow af = createBaseFlow(account);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_WITHDRAW);
        af.setAmount(m.getAmount().subtract(m.getChargeFee()));
        af.setNote("提现成功" + m.getAmount().subtract(m.getChargeFee()));
        accountFlowMapper.insert(af);
    }

    @Override
    public void withDrawChargeFlow(Account account, MoneyWithDraw m) {
        AccountFlow af = createBaseFlow(account);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);
        af.setAmount(m.getChargeFee());
        af.setNote("支付提现手续" + m.getChargeFee());
        accountFlowMapper.insert(af);
    }

    @Override
    public void withDrawFaildFlow(Account account, MoneyWithDraw m) {
        AccountFlow af = createBaseFlow(account);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_WITHDRAW_UNFREEZED);
        af.setAmount(m.getAmount());
        af.setNote("提现失败,退回冻结金额" + m.getAmount());
        accountFlowMapper.insert(af);
    }

    public void returnMoneyFlow(Account account, PaymentSchedule pay) {
        AccountFlow af = createBaseFlow(account);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_RETURN_MONEY);
        af.setAmount(pay.getTotalAmount());
        af.setNote("还款成功,扣除可用余额" + pay.getTotalAmount());
        accountFlowMapper.insert(af);
    }

    public void receiveFlow(Account bidAccount, PaymentScheduleDetail psd) {
        AccountFlow af = createBaseFlow(bidAccount);
        af.setActionType(BidConst.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY);
        af.setAmount(psd.getTotalAmount());
        af.setNote("回款成功,增加可用余额" + psd.getTotalAmount());
        accountFlowMapper.insert(af);
    }

    @Override
    public void interestChargeFlow(Account bidAccount, BigDecimal interestManagerCharge, PaymentScheduleDetail psd) {
        AccountFlow af = createBaseFlow(bidAccount);
        af.setActionType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE);
        af.setAmount(interestManagerCharge);
        af.setNote("缴纳利息管理费,可用余额减少" + interestManagerCharge);
        accountFlowMapper.insert(af);
    }
}
