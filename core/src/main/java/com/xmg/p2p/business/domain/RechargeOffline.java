package com.xmg.p2p.business.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmg.p2p.base.domain.BaseAuthDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Panda on 2016/11/6.
 */
@Getter@Setter
public class RechargeOffline extends BaseAuthDomain {
    private PlatformBankInfo bankInfo;
    private String tradeCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GTM+8")
    private Date tradeTime;
    private String note;
    private BigDecimal amount;

    public String getJsonString() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", getUsername());
        map.put("tradeCode", tradeCode);
        map.put("amount", amount);
        map.put("tradeTime", tradeTime);
        return JSON.toJSONString(map);
    }
}
