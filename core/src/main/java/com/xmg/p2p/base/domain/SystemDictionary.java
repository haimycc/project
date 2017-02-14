package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Panda on 2016/10/28.
 */
@Setter
@Getter
public class SystemDictionary extends BaseDomain {

    private String title; //数据字典标题

    private String sn;//数据字典英文标识

    private String intro;//数据字典分类使用说明

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("sn", sn);
        json.put("title", title);
        return JSON.toJSONString(json);
    }
}
