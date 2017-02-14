package com.xmg.p2p.business.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.query.PaymentScheduleQueryObject;

/**
 * Created by Panda on 2016/11/9.
 */
public interface IPaymentScheduleServcie {
    PageResult query(PaymentScheduleQueryObject qo);

    /**
     * 还款功能
     * @param id
     */
    void returnMoney(Long id);
}
