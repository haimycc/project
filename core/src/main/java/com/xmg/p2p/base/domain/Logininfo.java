package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录信息
 */
@Getter@Setter
public class Logininfo extends BaseDomain{
    public static final int STATE_NORMAL = 0;//普通状态
    public static final int STATE_LOCK = 1;//普通状态
    public static final int ROLE_USER = 0;
    public static final int ROLE_MANAGER = 1;

    private String username;

    private String password;

    private int state = STATE_NORMAL;

    private int userType;

    private String image;

}