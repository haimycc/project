package com.xmg.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryObject {
    private Integer currentPage = 1;
    private Integer pageSize = 5;

    public Integer getBeginIndex() {
        return (currentPage - 1) * pageSize;
    }

}
