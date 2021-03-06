package com.xmg.p2p.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Panda on 2016/11/8.
 */
@Getter
@Setter
public class SystemAccountFlow extends BaseDomain {
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;
    private BigDecimal amount;
    private int actionType;
    private String note;
    private BigDecimal userableAmount = BidConst.ZERO;//变化之后的可用余额
    private BigDecimal freezedAmount = BidConst.ZERO;//变化之后,账户的冻结余额
}
