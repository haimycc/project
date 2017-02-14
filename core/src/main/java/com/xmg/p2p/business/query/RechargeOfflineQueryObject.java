package com.xmg.p2p.business.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmg.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by Panda on 2016/11/6.
 */
@Getter@Setter
public class RechargeOfflineQueryObject extends QueryObject{
    private int state = -1;
    private Long bankInfoId = -1l;
    private String tradeCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GTM+8")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GTM+8")
    private Date endDate;

    public String getTradeCode() {
        return StringUtils.hasLength(tradeCode) ? tradeCode : null;
    }
}
