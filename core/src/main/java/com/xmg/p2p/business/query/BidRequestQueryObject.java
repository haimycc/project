package com.xmg.p2p.business.query;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.util.BidRequestOrderBy;
import com.xmg.p2p.business.util.BidRequestOrderType;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Panda on 2016/11/5.
 */
@Getter@Setter
public class BidRequestQueryObject extends QueryObject {
    private int bidRequestState = -1;
    private int[] states;

    private BidRequestOrderBy orderBy;
    private BidRequestOrderType orderType;
}
