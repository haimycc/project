package com.xmg.p2p.business.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;

/**
 * Created by Panda on 2016/11/6.
 */
public interface IRechargeOfflineService {
    RechargeOffline selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeOffline record);

    void apply(RechargeOffline ro);

    PageResult query(RechargeOfflineQueryObject qo);

    /**
     * 审核充值对象
     * @param id
     * @param remark
     * @param state
     */
    void audit(Long id, String remark, int state);
}
