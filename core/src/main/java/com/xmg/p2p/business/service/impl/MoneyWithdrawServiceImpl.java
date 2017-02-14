package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.MoneyWithDraw;
import com.xmg.p2p.business.domain.UserBankinfo;
import com.xmg.p2p.business.mapper.MoneyWithDrawMapper;
import com.xmg.p2p.business.query.MoneyWithDrawQueryObject;
import com.xmg.p2p.business.service.IAccountFlowService;
import com.xmg.p2p.business.service.IMoneyWithdrawService;
import com.xmg.p2p.business.service.ISystemAccountService;
import com.xmg.p2p.business.service.IUserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Panda on 2016/11/9.
 */
@Service
public class MoneyWithdrawServiceImpl implements IMoneyWithdrawService {
    @Autowired
    private MoneyWithDrawMapper moneyWithDrawMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private IUserBankinfoService bankinfoService;
    @Autowired
    private ISystemAccountService systemAccountService;

    public int insert(MoneyWithDraw record) {
        return moneyWithDrawMapper.insert(record);
    }

    public MoneyWithDraw selectByPrimaryKey(Long id) {
        return moneyWithDrawMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(MoneyWithDraw record) {
        return moneyWithDrawMapper.updateByPrimaryKey(record);
    }

    public void apply(BigDecimal moneyAmount) {
        //判断用户是否已经已经有申请
        UserInfo current = userInfoService.getCurrent();
        Account account = accountService.getCurrent();
        //没有其他提现和绑定了银行卡才能提现,大于最小提现金额,小于账户可用余额
        if (!current.getHasWithDrawInProcess()
                && current.getIsBindBank()
                && moneyAmount.compareTo(BidConst.MIN_WITHDRAW_AMOUNT) >= 0
                && moneyAmount.compareTo(account.getUsableAmount()) <= 0
                ) {
            //创建一个提现对象
            UserBankinfo bank = bankinfoService.selectByUserId(current.getId());
            MoneyWithDraw m = new MoneyWithDraw();
            m.setAccountName(bank.getAccountName());
            m.setAccountNumber(bank.getAccountNumber());
            m.setAmount(moneyAmount);
            m.setApplier(UserContext.getCurrent());
            m.setApplyTime(new Date());
            m.setBankName(bank.getBankName());
            m.setChargeFee(BidConst.MONEY_WITHDRAW_CHARGEFEE);
            m.setBankForkName(bank.getBankForkName());
            m.setState(MoneyWithDraw.STATE_NORMAL);
            moneyWithDrawMapper.insert(m);
            account.setFreezedAmount(account.getFreezedAmount().add(moneyAmount));
            account.setUsableAmount(account.getUsableAmount().subtract(moneyAmount));
            accountFlowService.withDrawFlow(account, m);
            //用户添加状态码
            current.addState(BitStatesUtils.HAS_WITHDRAW_IN_PROCESS);
            userInfoService.update(current);
            accountService.update(account);
        }
    }

    public Object query(MoneyWithDrawQueryObject qo) {
        int totalCount = moneyWithDrawMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<MoneyWithDraw> listData = moneyWithDrawMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int state) {
        //拿到提现对象
        MoneyWithDraw m = moneyWithDrawMapper.selectByPrimaryKey(id);
        //如果对象存在,并在处于被审核状态就进行审核
        if (m != null && m.getState() == MoneyWithDraw.STATE_NORMAL) {
            m.setRemark(remark);
            m.setAuditTime(new Date());
            m.setAuditor(UserContext.getCurrent());
            //审核通过
            m.setState(state);
            Account account = accountService.get(m.getApplier().getId());
            UserInfo applier = userInfoService.get(m.getApplier().getId());
            applier.removeState(BitStatesUtils.HAS_WITHDRAW_IN_PROCESS);//移除状态码
            if (state == MoneyWithDraw.STATE_AUDIT) {
                //审核通过
                //用户冻结金额减少,生成提现交易流水
                account.setFreezedAmount(account.getFreezedAmount().subtract(m.getAmount()).add(BidConst.MONEY_WITHDRAW_CHARGEFEE));
                accountFlowService.withDrawSuccessFlow(account, m);
                //用户扣除交易手续费,生成提现扣除交易手续费流水
                account.setFreezedAmount(account.getFreezedAmount().subtract(m.getChargeFee()));
                accountFlowService.withDrawChargeFlow(account, m);
                //移除用户提现流程状态码
                //系统账户增加提现手续费,并生成交易流水
                systemAccountService.chargeWithDrawFee(m);
            } else {
                //用户冻结金额减少,可用余额增加,生成提现失败交易流水
                account.setFreezedAmount(account.getFreezedAmount().subtract(m.getAmount()));
                account.setUsableAmount(account.getUsableAmount().add(m.getAmount()));
                accountFlowService.withDrawFaildFlow(account, m);
            }
            accountService.update(account);
            userInfoService.update(applier);
            moneyWithDrawMapper.updateByPrimaryKey(m);
        }
    }
}
