package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Panda on 2016/11/4.
 */
@Getter
@Setter
public class Bid extends BaseDomain {

    private BigDecimal actualRate;//实际年利率(应该是等同于标的的利率)
    private BigDecimal availableAmount;//投标有效金额(就是投标金额)
    private Long bidRequestId;//来自于哪个借款标
    private String bidRequestTitle;//标的名称（冗余数据）
    private Logininfo bidUser;//投标人
    private Date bidTime;//投标时间
    private int bidRequestState;//标的状态，应该和标的状态同步

}
