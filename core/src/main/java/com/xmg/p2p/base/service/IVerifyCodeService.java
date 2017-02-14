package com.xmg.p2p.base.service;

/**
 * 处理短信验证码服务
 * Created by Panda on 2016/10/31.
 */
public interface IVerifyCodeService {

    /**
     * 发送短信验证码
     * @param phoneNumber
     */
    void sendVerifyCode(String phoneNumber);

    /**
     * 判定验证码
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
    boolean vaild(String phoneNumber, String verifyCode);

}
