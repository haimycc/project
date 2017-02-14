package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.PaymentScheduleDetail;

public interface PaymentScheduleDetailMapper {

    int insert(PaymentScheduleDetail record);

    int updateByPrimaryKey(PaymentScheduleDetail record);
}