package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.mapper.RechargeOfflineMapper;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;
import com.xmg.p2p.business.service.IAccountFlowService;
import com.xmg.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Panda on 2016/11/6.
 */
@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;

    public RechargeOffline selectByPrimaryKey(Long id) {
        return rechargeOfflineMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(RechargeOffline record) {
        return rechargeOfflineMapper.updateByPrimaryKey(record);
    }

    @Override
    public void apply(RechargeOffline ro) {
        //如果充值对象不为空,就创建一个新的充值对象,保存数据
        if (ro != null) {
            RechargeOffline r = new RechargeOffline();
            r.setApplyTime(new Date());
            r.setApplier(UserContext.getCurrent());
            r.setAmount(ro.getAmount());
            r.setBankInfo(ro.getBankInfo());
            r.setNote(ro.getNote());
            r.setTradeCode(ro.getTradeCode());
            r.setTradeTime(new Date());
            r.setState(RechargeOffline.STATE_NORMAL);
            rechargeOfflineMapper.insert(r);
        }
    }

    public PageResult query(RechargeOfflineQueryObject qo) {
        int totalCount = rechargeOfflineMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<RechargeOffline> listData = rechargeOfflineMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int state) {
        //得到充值对象
        //如果充值对象存在,并且未审核,就进行审核
        RechargeOffline r = selectByPrimaryKey(id);
        if (r != null && r.getState() == RechargeOffline.STATE_NORMAL) {
            //设置相关属性
            r.setRemark(remark);
            r.setAuditTime(new Date());
            r.setAuditor(UserContext.getCurrent());
            r.setState(state);
            if (state == RechargeOffline.STATE_AUDIT) {
                //审核通过得到用户账户,增加可用余额
                Account account = accountService.get(r.getApplier().getId());
                account.setUsableAmount(account.getUsableAmount().add(r.getAmount()));
                //添加一条账户流水
                accountFlowService.rechargeFlow(account, r);
                accountService.update(account);
            }
            rechargeOfflineMapper.updateByPrimaryKey(r);
        }
    }
}
