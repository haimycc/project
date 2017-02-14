package com.xmg.p2p.base.util;

/**
 * 用户状态类，记录用户在平台使用系统中所有的状态。
 *
 * @author Administrator
 */
public class BitStatesUtils {
    public final static Long OP_BIND_PHONE = 1L << 0; //2 用户绑定手机状态码
    public final static Long OP_BIND_EMAIL = 1L << 1; //4 用户绑定邮箱状态码
    public final static Long OP_BASIC_INFO = 1L << 2; //6 用户填写基本资料
    public final static Long OP_REAL_AUTH  = 1L << 3; //8 用户实名认证
    public final static Long OP_VEDIO_AUTH = 1L << 4; // 16用户视频认证
    public final static Long HAS_BIDREQUEST_IN_PROCESS = 1L << 5; //32 判断用户是否在借款流程当中
    public final static Long OP_BIND_BANK = 1L << 6; //64 判断用户是否绑定了银行卡
    public final static Long HAS_WITHDRAW_IN_PROCESS = 1L << 7; //128 判断用户是否有提现请求


    /**
     * @param states 所有状态值
     * @param value  需要判断状态值
     * @return 是否存在
     */
    public static boolean hasState(long states, long value) {
        return (states & value) != 0;
    }

    /**
     * @param states 已有状态值
     * @param value  需要添加状态值
     * @return 新的状态值
     */
    public static long addState(long states, long value) {
        if (hasState(states, value)) {
            return states;
        }
        return (states | value);
    }

    /**
     * @param states 已有状态值
     * @param value  需要删除状态值
     * @return 新的状态值
     */
    public static long removeState(long states, long value) {
        if (!hasState(states, value)) {
            return states;
        }
        return states ^ value;
    }
}
