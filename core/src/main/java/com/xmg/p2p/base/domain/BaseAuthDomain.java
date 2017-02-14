package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Panda on 2016/11/3.
 */
@Getter
@Setter
public class BaseAuthDomain extends BaseDomain {
    public static final int STATE_NORMAL = 0;//未审核
    public static final int STATE_AUDIT = 1;//审核通过
    public static final int STATE_REJECT = 2;//审核拒绝

    protected int state;  //审核状态
    protected Logininfo applier;//申请人
    protected Date applyTime;//申请时间
    protected Logininfo auditor;//审核人
    protected Date auditTime;//审核时间
    protected String remark;//审核备注

    public String getStateDisplay() {
        switch (state) {
            case STATE_NORMAL:
                return "待审核";
            case STATE_AUDIT:
                return "审核通过";
            case STATE_REJECT:
                return "审核拒绝";
            default:
                return "";
        }
    }

    public String getUsername() {
        return applier.getUsername();
    }


}
