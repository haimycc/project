package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.query.BidRequestQueryObject;

import java.util.List;

public interface BidRequestMapper {
    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);


    int queryForCount(BidRequestQueryObject qo);

    List<BidRequest> query(BidRequestQueryObject qo);

    void update(BidRequest br);

    /**
     * 查询发标过程中的标
     * @return
     */
    List<BidRequest> listInvest(BidRequestQueryObject qo);


}