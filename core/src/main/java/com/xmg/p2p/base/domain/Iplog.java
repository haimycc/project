package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 登陆日志
 * Created by Panda on 2016/10/29.
 */
@Getter
@Setter
public class Iplog extends BaseDomain {
    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAILED = 1;

    private String ip;
    private Date loginTime;
    private int state;
    private String username;
    private int userType;

    public String getStateDisplay() {
        return state == LOGIN_SUCCESS ? "登陆成功" : "登陆失败";
    }

    public String getUserTypeDisplay() {
        return userType == Logininfo.ROLE_MANAGER ? "管理员" : "前台用户";
    }
}
