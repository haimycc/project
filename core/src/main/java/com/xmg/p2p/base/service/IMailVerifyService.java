package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.MailVerify;

/**
 * Created by Panda on 2016/10/31.
 */
public interface IMailVerifyService {
    int insert(MailVerify record);

    MailVerify selectByKey(String uuid);

    /**
     * 发送邮件
     * @param email
     */
    void sendEmail(String email);

    /**
     * 绑定邮箱
     */
    void bindEmail(String uuid);
}
