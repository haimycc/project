package com.xmg.p2p.base.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * Created by Panda on 2016/11/1.
 */
@Getter@Setter
public class SystemDictionaryQueryObject extends QueryObject {
    private String keyword;
    private Long parentId = -1L;

    public String getKeyword() {
        return StringUtils.hasLength(keyword) ? keyword : null;
    }

}
