package com.xmg.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 发送到session中的验证码相关内容
 * Created by Panda on 2016/10/31.
 */

@Getter@Setter
public class VerifyCodeVO implements Serializable{


    private String phoneNumber;
    private Date sendTime;
    private String verifyCode;
}
