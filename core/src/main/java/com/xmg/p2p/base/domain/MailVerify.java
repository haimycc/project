package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Panda on 2016/10/31.
 */
@Setter
@Getter
public class MailVerify extends BaseDomain {
    private Long userinfoId;
    private String uuid;
    private Date sendTime;
    private String email;
}
