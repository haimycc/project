package com.xmg.p2p.base.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * Created by Panda on 2016/11/3.
 */
@Getter@Setter
public class VideoAuthQueryObject extends AuthQueryObject{
    private String keyword;

    public String getKeyword() {
        return StringUtils.hasLength(keyword) ? keyword.trim() : null;
    }
}
