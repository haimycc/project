package com.xmg.p2p.business.domain;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseAuthDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 提现对象
 * Created by Panda on 2016/11/9.
 */
@Getter@Setter
public class MoneyWithDraw extends BaseAuthDomain {
    private BigDecimal amount;
    private BigDecimal chargeFee;

    private String bankName;
    private String accountName;
    private String accountNumber;
    private String bankForkName;

    public String getJsonString() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", getUsername());
        map.put("realName", accountName);
        map.put("applyTime", applyTime);
        map.put("bankName", bankName);
        map.put("accountNumber", accountNumber);
        map.put("bankforkname", bankForkName);
        map.put("moneyAmount", amount);
        return JSON.toJSONString(map);
    }
}
