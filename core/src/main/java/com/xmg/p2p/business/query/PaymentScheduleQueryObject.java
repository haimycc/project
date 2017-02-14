package com.xmg.p2p.business.query;

import com.xmg.p2p.base.query.AuthQueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Panda on 2016/11/9.
 */
@Getter
@Setter
public class PaymentScheduleQueryObject extends AuthQueryObject {
    private Long borrowUserId;
    private Long bidRequestId;
}
