package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.Bid;
import org.apache.ibatis.annotations.Param;

public interface BidMapper {
    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);

    /**
     * 批量修改投标对象状态
     * @param bidRequestId
     * @param bidrequestStateApprovePending1
     */
    void updateStates(@Param("bidRequestId") Long bidRequestId, @Param("state") int bidrequestStateApprovePending1);
}