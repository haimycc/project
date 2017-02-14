package com.xmg.p2p.business.service;

import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Panda on 2016/11/4.
 */
public interface IBidRequestService {

    void update(BidRequest br);

    BidRequest get(Long id);


    /**
     * 申请借款
     * @param bidRequest
     */
    void apply(BidRequest bidRequest);

    /**
     * 高级查询加分页
     * @param qo
     * @return
     */
    PageResult query(BidRequestQueryObject qo);

    /**
     * 审核
     * @param id
     * @param remark
     * @param state
     */
    void audit(Long id, String remark, int state);

    /**
     * 根据一个标查询该标的审核历史
     *
     * @param id
     * @return
     */
    List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id);

    /**
     * 查询
     * @return
     */
    List<BidRequest> listInvest(BidRequestQueryObject qo);

    /**
     * 投资方法
     * @param bidRequestId
     * @param amount
     */
    void bid(Long bidRequestId, BigDecimal amount);

    /**
     * 一审
     * @param id
     * @param remark
     * @param state
     */
    void audit1(Long id, String remark, int state);

    /**
     * 二审
     * @param id
     * @param remark
     * @param state
     */
    void audit2(Long id, String remark, int state);
}
