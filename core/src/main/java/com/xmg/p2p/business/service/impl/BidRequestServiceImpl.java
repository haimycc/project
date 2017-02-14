package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.*;
import com.xmg.p2p.business.mapper.*;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IAccountFlowService;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.service.ISystemAccountService;
import com.xmg.p2p.business.util.CalculatetUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Panda on 2016/11/4.
 */
@Service
public class BidRequestServiceImpl implements IBidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Autowired
    private PaymentScheduleDetailMapper PaymentScheduleDetailMapper;

    @Override
    public void update(BidRequest br) {
        bidRequestMapper.update(br);
    }

    @Override
    public BidRequest get(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    public void apply(BidRequest bidRequest) {
        //判断当前用户是否还在借款流程中
        //判断用户是否满足四项借款条件
        //判断借款利息实在在5-20之间
        //判断最小投标是否在50-当前借款数之间
        //借款金额是否在贷款额度以内
        UserInfo current = userInfoService.getCurrent();
        Account account = accountService.getCurrent();
        if (!current.getHasBidRequestInProcess()
                && current.getHasBidRequest()
                && bidRequest.getCurrentRate().compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0
                && bidRequest.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0
                && bidRequest.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0
                && bidRequest.getMinBidAmount().compareTo(bidRequest.getBidRequestAmount()) <= 0
                && bidRequest.getBidRequestAmount().compareTo(account.getRemainBorrowLimit()) <= 0
                ) {
            //创建一个借款对象
            //并填充资料
            BidRequest br = new BidRequest();
            br.setApplyTime(new Date());
            br.setBidRequestAmount(bidRequest.getBidRequestAmount());
            br.setBidRequestState(bidRequest.getBidRequestState());
            br.setBidRequestType(bidRequest.getBidRequestType());
            br.setCreateUser(UserContext.getCurrent());
            br.setCurrentRate(bidRequest.getCurrentRate());
            br.setDescription(bidRequest.getDescription());
            br.setDisableDays(bidRequest.getDisableDays());
            br.setMinBidAmount(bidRequest.getMinBidAmount());
            br.setMonthes2Return(bidRequest.getMonthes2Return());
            br.setReturnType(bidRequest.getReturnType());
            br.setTitle(bidRequest.getTitle());
            br.setTotalRewardAmount(CalculatetUtil.calTotalInterest(br.getReturnType(), br.getBidRequestAmount(), bidRequest.getCurrentRate(), br.getMonthes2Return()));
            bidRequestMapper.insert(br);
            current.addState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
            userInfoService.update(current);
        }
    }

    @Override
    public PageResult query(BidRequestQueryObject qo) {
        int totalCount = bidRequestMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<BidRequest> listData = bidRequestMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int state) {
        //先拿到审核对象
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        System.out.println(br.getCreateUser());
        //判断借款对象是否未审核
        if (br != null && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING) {
            //创建一个借款审核对象
            creatbidRequestAuditHistory(id, remark, state, br, BidRequestAuditHistory.PUBLISH_AUDIT);

            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                // 如果审核通过:修改标的状态,设置风控意见;
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
                br.setDisableDate(DateUtils.addDays(new Date(),
                        br.getDisableDays()));
                br.setPublishTime(new Date());
                br.setNote(remark);
            } else {
                // 如果审核失败:修改标的状态,用户去掉状态码;
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
                UserInfo applier = userInfoService.get(br.getCreateUser()
                        .getId());
                applier.removeState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
                userInfoService.update(applier);
            }
            update(br);
        }
    }


    public List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id) {
        return this.bidRequestAuditHistoryMapper.listByBidRequest(id);
    }

    public List<BidRequest> listInvest(BidRequestQueryObject qo) {
        return bidRequestMapper.listInvest(qo);
    }

    public void bid(Long bidRequestId, BigDecimal amount) {
        //先拿到借款对象,判断该对象是否存在,是否处于借款状态
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(bidRequestId);
        Account bidAccount = accountService.getCurrent();
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_BIDDING
                && amount.compareTo(bidRequest.getMinBidAmount()) >= 0 //充值金额大于最小充值金额
                && amount.compareTo(bidRequest.getRemainAmount()) <= 0 //充值金额小于还差金额
                && !UserContext.getCurrent().getId().equals(bidRequest.getCreateUser().getId())
                && amount.compareTo(bidAccount.getUsableAmount()) <= 0)//充值金额小于账户可用余额
        {
            //执行投标操作
            //创建bid对象
            Bid bid = new Bid();
            bid.setBidTime(new Date());
            bid.setBidRequestTitle(bidRequest.getTitle());
            bid.setActualRate(bidRequest.getCurrentRate());
            bid.setAvailableAmount(amount);
            bid.setBidUser(UserContext.getCurrent());
            bid.setBidRequestId(bidRequestId);
            bid.setBidRequestState(bidRequest.getBidRequestState());
            bidMapper.insert(bid);
            //2.修改标上面的相关属性
            bidRequest.setCurrentSum(bidRequest.getCurrentSum().add(amount));
            bidRequest.setBidCount(bidRequest.getBidCount() + 1);
            //3.得到投标人账户,执行投标相关操作,生成投标流水
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().subtract(amount));
            bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().add(amount));
            accountFlowService.bidFlow(bidAccount, bid);

            //判断标是否已满,修改标的状态
            if (bidRequest.getRemainAmount().compareTo(BidConst.ZERO) == 0) {
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
                bidMapper.updateStates(bidRequestId, BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
            }
            accountService.update(bidAccount);
            update(bidRequest);
        }
    }

    @Override
    public void audit1(Long id, String remark, int state) {
        //判断标的状态处于满标1审
        //创建一个审核对象
        BidRequest bidRequest = get(id);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1) {
            creatbidRequestAuditHistory(id, remark, state, bidRequest, BidRequestAuditHistory.FULL_AUDIT_1);
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                //审核通过,修改标的状态到满标二审
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
                bidMapper.updateStates(id, BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
            } else {
                returnMoney(bidRequest);
            }
            update(bidRequest);
        }
    }


    @Override
    public void audit2(Long id, String remark, int state) {
        BidRequest bidRequest = get(id);
        if (bidRequest != null && bidRequest.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2) {
            //审核通过
            //创建一个借款审核对象
            creatbidRequestAuditHistory(id, remark, state, bidRequest, BidRequestAuditHistory.FULL_AUDIT_2);
            if (state == BidRequestAuditHistory.STATE_AUDIT) {
                //审核通过
                //借款对象
                //1.修改借款状态为还款中
                //2.所有的投标状态修改为还款中
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);
                bidMapper.updateStates(id, BidConst.BIDREQUEST_STATE_PAYING_BACK);

                //2.对于借款人
                //2.1得到借款人账户,可用余额增加
                Account borrowAccount = accountService.get(bidRequest.getCreateUser().getId());
                borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().add(bidRequest.getBidRequestAmount()));
                //2.2生成成功借款流水
                accountFlowService.borrowSuccessFlow(borrowAccount, bidRequest);
                //3剩余信用额度减少
                borrowAccount.setRemainBorrowLimit(borrowAccount.getRemainBorrowLimit().subtract(bidRequest.getBidRequestAmount()));
                //4.待还本息增加
                borrowAccount.setUnReturnAmount(borrowAccount.getUnReturnAmount()
                        .add(bidRequest.getBidRequestAmount()
                                .add(bidRequest.getTotalRewardAmount()
                                )));
                //5.去掉借款中状态码
                UserInfo borrowUser = userInfoService.get(borrowAccount.getId());
                borrowUser.removeState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
                //6.借款人支付借款手续费,可用余额减少
                BigDecimal borrowChargeFee = CalculatetUtil.calAccountManagementCharge(bidRequest.getBidRequestAmount());
                borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().subtract(borrowChargeFee));
                //7.支付手续费流水
                //流水要及时生成
                accountFlowService.borrowChargeFlow(borrowAccount, bidRequest, borrowChargeFee);
                //8.平台账户收取借款手续费,流水
                systemAccountService.chargeBorrowFee(borrowChargeFee, bidRequest);
                //3.对于投资人
                Map<Long, Account> updates = new HashMap<>();
                for (Bid bid : bidRequest.getBids()) {
                    //遍历投标,得到投资人账户
                    //代收本金,利息增加
                    //冻结金额减少,生成成功投标流水
                    Long bidAccountId = bid.getBidUser().getId();
                    Account bidAccount = updates.get(bidAccountId);
                    if (bidAccount == null) {
                        bidAccount = accountService.get(bidAccountId);
                        updates.put(bidAccountId, bidAccount);
                    }
                    bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
                    accountFlowService.bidSuccessFlow(bidAccount, bid);
                }
                //4 满标二审
                //根据接口生成对于的还款对象和还款明细
                List<PaymentSchedule> pss = createPaymentSchedule(bidRequest);
                for (PaymentSchedule ps : pss) {
                    for (PaymentScheduleDetail psd : ps.getPaymentScheduleDetails()) {
                        Account bidAccount = updates.get(psd.getToLogininfoId());
                        bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().add(psd.getInterest()));
                        bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().add(psd.getPrincipal()));
                    }
                }
                userInfoService.update(borrowUser);
                accountService.update(borrowAccount);
                for (Account update : updates.values()) {
                    accountService.update(update);
                }

            } else {
                //审核失败
                //和一审失败一样
                returnMoney(bidRequest);
            }
            update(bidRequest);
        }


    }


    /**
     * 借款审核对象
     *
     * @param id
     * @param remark
     * @param state
     * @param bidRequest
     * @param auditType
     */
    private void creatbidRequestAuditHistory(Long id, String remark, int state, BidRequest bidRequest, int auditType) {
        BidRequestAuditHistory h = new BidRequestAuditHistory();
        h.setApplier(bidRequest.getCreateUser());
        h.setApplyTime(new Date());
        h.setAuditor(UserContext.getCurrent());
        h.setAuditTime(new Date());
        h.setAuditType(auditType);
        h.setBidRequestId(id);
        h.setRemark(remark);
        h.setState(state);
        bidRequestAuditHistoryMapper.insert(h);
    }

    /**
     * 审核失败,还钱
     *
     * @param bidRequest
     */
    private void returnMoney(BidRequest bidRequest) {
        //审核失败
        //1.修改标的状态
        bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
        //修改投标对象的状态
        bidMapper.updateStates(bidRequest.getId(), BidConst.BIDREQUEST_STATE_REJECTED);
        //2.去掉借款人的状态码
        UserInfo borrowUser = userInfoService.get(bidRequest.getCreateUser().getId());
        borrowUser.removeState(BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
        userInfoService.update(borrowUser);
        //3.遍历投标对象,得到每一个投资人,还款,生成投标失败流水

        Map<Long, Account> updates = new HashMap<>();
        for (Bid bid : bidRequest.getBids()) {
            Long bidUserId = bid.getBidUser().getId();
            //得到投资人账户
            Account bidAccount = updates.get(bidUserId);
            if (bidAccount == null) {
                bidAccount = accountService.get(bidUserId);
                updates.put(bidUserId, bidAccount);
            }
            //冻结金额减少,可用余额增加
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(bid.getAvailableAmount()));
            bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));

            //投资失败流水
            accountFlowService.bidFaildFlow(bidAccount, bid);
        }
        for (Account update : updates.values()) {
            accountService.update(update);
        }
    }

    /**
     * 创建还款对象
     */
    private List<PaymentSchedule> createPaymentSchedule(BidRequest bidRequest) {
        BigDecimal totalAcount = BidConst.ZERO;
        BigDecimal totalInterest = BidConst.ZERO;
        List<PaymentSchedule> ret = new ArrayList<>();
        for (int i = 0; i < bidRequest.getMonthes2Return(); i++) {
            PaymentSchedule p = new PaymentSchedule();
            p.setBidRequestId(bidRequest.getId());
            p.setBidRequestTitle(bidRequest.getTitle());
            p.setBidRequestType(bidRequest.getReturnType());
            p.setBorrowUser(bidRequest.getCreateUser());
            p.setReturnType(bidRequest.getReturnType());
            p.setState(BidConst.PAYMENT_STATE_NORMAL);
            p.setDeadLine(DateUtils.addMonths(bidRequest.getPublishTime(), i + 1));
            p.setMonthIndex(i + 1);
            p.setInterest(CalculatetUtil.calMonthlyInterest(bidRequest.getReturnType(), bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), i + 1, bidRequest.getMonthes2Return()));
            p.setPrincipal(CalculatetUtil.calMonthToReturnMoney(bidRequest.getReturnType(), bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), i + 1, bidRequest.getMonthes2Return()));
            p.setTotalAmount(p.getInterest().add(p.getPrincipal()));
            //最后一期 做平衡
            if (i == bidRequest.getMonthes2Return() - 1) {
                p.setPrincipal(bidRequest.getBidRequestAmount().subtract(totalAcount));
                p.setInterest(bidRequest.getTotalRewardAmount().subtract(totalInterest));
                p.setTotalAmount(p.getInterest().add(p.getPrincipal()));
            }
            paymentScheduleMapper.insert(p);
            createPaymentScheduleDetail(p, bidRequest);
            ret.add(p);
            totalAcount = totalAcount.add(p.getPrincipal());
            totalInterest = totalInterest.add(p.getInterest());
        }
        return ret;
    }

    private void createPaymentScheduleDetail(PaymentSchedule p, BidRequest bidRequest) {
        BigDecimal totalAcount = BidConst.ZERO;
        BigDecimal totalInterest = BidConst.ZERO;
        for (int i = 0; i < bidRequest.getBids().size(); i++) {
            PaymentScheduleDetail ps = new PaymentScheduleDetail();
            ps.setBidRequestId(p.getBidRequestId());
            ps.setDeadLine(p.getDeadLine());
            ps.setFromLogininfo(p.getBorrowUser());
            ps.setPaymentScheduleId(p.getId());
            ps.setReturnType(p.getReturnType());
            ps.setBidRequestTitle(p.getBidRequestTitle());
            ps.setMonthIndex(p.getMonthIndex());
            Bid bid = bidRequest.getBids().get(i);
            BigDecimal rate = bid.getAvailableAmount().divide(bidRequest.getBidRequestAmount(), 8, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);
            ps.setBidAmount(bid.getAvailableAmount());
            ps.setBidId(bid.getId());
            ps.setToLogininfoId(bid.getBidUser().getId());

            ps.setPrincipal(rate.multiply(p.getPrincipal()));
            ps.setInterest(rate.multiply(p.getInterest()));
            ps.setTotalAmount(ps.getPrincipal().add(ps.getInterest()));
            //最后一期 做平衡
            if (i == bidRequest.getBids().size() - 1) {
                ps.setPrincipal(p.getPrincipal().subtract(totalAcount));
                ps.setInterest(p.getInterest().subtract(totalInterest));
                ps.setTotalAmount(ps.getInterest().add(ps.getPrincipal()));
            }
            PaymentScheduleDetailMapper.insert(ps);
            p.getPaymentScheduleDetails().add(ps);
            totalAcount = totalAcount.add(ps.getPrincipal());
            totalInterest = totalInterest.add(ps.getInterest());
        }
    }
}
