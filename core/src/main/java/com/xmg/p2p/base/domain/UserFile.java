package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Panda on 2016/11/3.
 */
@Getter
@Setter
public class UserFile extends BaseAuthDomain {
    public static final int ZERO = 0;
    private SystemDictionaryItem fileType;
    private int score;
    private String image;

    public String getJsonString() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("applier", applier.getUsername());
        map.put("fileType", fileType.getTitle());
        map.put("image", image);
        System.out.println(map);
        return JSON.toJSONString(map);
    }
}
