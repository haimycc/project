package com.xmg.p2p.business.service;

import com.xmg.p2p.business.domain.MoneyWithDraw;
import com.xmg.p2p.business.query.MoneyWithDrawQueryObject;

import java.math.BigDecimal;

/**
 * Created by Panda on 2016/11/9.
 */
public interface IMoneyWithdrawService {
    int insert(MoneyWithDraw record);

    MoneyWithDraw selectByPrimaryKey(Long id);

    int updateByPrimaryKey(MoneyWithDraw record);

    /**
     * 提现申请方法
     * @param moneyAmount
     */
    void apply(BigDecimal moneyAmount);

    /**
     * 高级查询
     * @param qo
     * @return
     */
    Object query(MoneyWithDrawQueryObject qo);

    /**
     * 提现审核
     * @param id
     * @param remark
     * @param state
     */
    void audit(Long id, String remark, int state);
}
