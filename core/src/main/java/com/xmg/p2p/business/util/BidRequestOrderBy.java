package com.xmg.p2p.business.util;

/**
 * Created by Panda on 2016/11/5.
 */

public enum BidRequestOrderBy {
    BIDREQUEST_STATE("br.bidRequestState"), BIDREQUEST_RATE("br.returnRate");

    private String propertyName;

    BidRequestOrderBy(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
