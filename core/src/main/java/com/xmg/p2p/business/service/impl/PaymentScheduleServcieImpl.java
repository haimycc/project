package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.PaymentSchedule;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;
import com.xmg.p2p.business.mapper.BidMapper;
import com.xmg.p2p.business.mapper.PaymentScheduleDetailMapper;
import com.xmg.p2p.business.mapper.PaymentScheduleMapper;
import com.xmg.p2p.business.query.PaymentScheduleQueryObject;
import com.xmg.p2p.business.service.IAccountFlowService;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.service.IPaymentScheduleServcie;
import com.xmg.p2p.business.service.ISystemAccountService;
import com.xmg.p2p.business.util.CalculatetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Panda on 2016/11/9.
 */
@Service
public class PaymentScheduleServcieImpl implements IPaymentScheduleServcie {
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private BidMapper bidMapper;

    public PageResult query(PaymentScheduleQueryObject qo) {
        int totalCount = paymentScheduleMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<PaymentSchedule> listData = paymentScheduleMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());

    }

    public void returnMoney(Long id) {
        //得到还款对象,看状态是否为正常还款
        PaymentSchedule pay = paymentScheduleMapper.selectByPrimaryKey(id);
        if (pay != null && pay.getState() == BidConst.PAYMENT_STATE_NORMAL) {
            //得到当前账户,看当前账户余额是否足够还款,还款人要是本人
            Account current = accountService.getCurrent();
            if (current.getId() == pay.getBorrowUser().getId()
                    && current.getUsableAmount().compareTo(pay.getTotalAmount()) >= 0) {
                //针对还款对象
                //还款日期,还款状态
                Date now = new Date();
                pay.setPayDate(now);
                pay.setState(BidConst.PAYMENT_STATE_DONE);

                //针对还款人
                //账户可用余额减少,生成还款成功交易流水
                //信用额度增加
                //待还本息减少
                current.setUsableAmount(current.getUsableAmount().subtract(pay.getTotalAmount()));
                current.setUnReturnAmount(current.getUnReturnAmount().subtract(pay.getTotalAmount()));
                current.setRemainBorrowLimit(current.getRemainBorrowLimit().add(pay.getPrincipal()));
                accountFlowService.returnMoneyFlow(current, pay);
                accountService.update(current);

                //针对投资人
                //遍历还款对象的所有的明细
                //明细的还款日期
                //得到投资账户,可用余额增加,生成收款流水
                //待收本金和待收利息减少
                //缴纳利息管理费,并生成流水
                //系统账户收取利息管理费,生成流水
                Map<Long, Account> updates = new HashMap<>();
                for (PaymentScheduleDetail psd : pay.getPaymentScheduleDetails()) {
                    psd.setPayDate(now);
                    Long bidId = psd.getToLogininfoId();
                    Account bidAccount = updates.get(bidId);
                    if (bidAccount == null) {
                        bidAccount = accountService.get(bidId);
                        updates.put(bidId, bidAccount);
                    }
                    bidAccount.setUsableAmount(bidAccount.getUsableAmount()
                            .add(psd.getTotalAmount()));
                    accountFlowService.receiveFlow(bidAccount, psd);
                    bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().subtract(psd.getInterest()));
                    bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().subtract(psd.getPrincipal()));
                    BigDecimal interestManagerCharge = CalculatetUtil.calInterestManagerCharge(psd.getInterest());
                    bidAccount.setUsableAmount(bidAccount.getUsableAmount().subtract(interestManagerCharge));
                    accountFlowService.interestChargeFlow(bidAccount, interestManagerCharge, psd);
                    //系统账户收取利息管理费
                    systemAccountService.chargeInterestFee(interestManagerCharge, psd);
                    paymentScheduleDetailMapper.updateByPrimaryKey(psd);
                }
                for (Account update : updates.values()) {
                    accountService.update(update);
                }
                paymentScheduleMapper.updateByPrimaryKey(pay);
                //针对借款对象
                BidRequest bidRequest = bidRequestService.get(pay.getBidRequestId());
                //得到借款对象的所有还款对象,如果所有还款都为已换
                PaymentScheduleQueryObject qo = new PaymentScheduleQueryObject();
                qo.setPageSize(-1);
                qo.setBidRequestId(bidRequest.getId());
                List<PaymentSchedule> pss = paymentScheduleMapper.query(qo);
                boolean find = false;
                for (PaymentSchedule temp : pss) {
                    if (temp.getState() == BidConst.PAYMENT_STATE_NORMAL) {
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);
                    //修改借款和投标的状态
                    bidMapper.updateStates(bidRequest.getId(), BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
                    bidRequestService.update(bidRequest);
                }

            }
        }
    }
}
