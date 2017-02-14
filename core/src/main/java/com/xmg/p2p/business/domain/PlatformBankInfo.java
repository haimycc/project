package com.xmg.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Panda on 2016/11/6.
 */
@Getter
@Setter
public class PlatformBankInfo extends BaseDomain {
    private String bankName;// 银行名称
    private String accountName;// 开户人姓名
    private String accountNumber;// 银行账号
    private String bankForkName;// 开户支行

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("bankName", this.bankName);
        json.put("accountName", accountName);
        json.put("accountNumber", accountNumber);
        json.put("bankForkName", bankForkName);
        return JSONObject.toJSONString(json);
    }

}
