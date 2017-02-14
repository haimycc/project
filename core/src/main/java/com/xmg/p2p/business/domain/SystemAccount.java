package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Panda on 2016/11/8.
 */
@Getter
@Setter
public class SystemAccount extends BaseDomain {
    private int version;
    private BigDecimal usableAmount = BidConst.ZERO;//系统账户余额
    private BigDecimal freezedAmount = BidConst.ZERO;//系统账户冻结金额
}
