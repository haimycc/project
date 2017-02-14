package com.xmg.p2p.base.service.impl;


import com.xmg.p2p.base.domain.MailVerify;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.mapper.MailVerifyMapper;
import com.xmg.p2p.base.service.IMailVerifyService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by Panda on 2016/10/31.
 */
@Service
public class MailVerifyServiceImpl implements IMailVerifyService {
    @Value("${email.host}")
    private String host;
    @Value("${email.username}")
    private String username;
    @Value("${email.sendEmail}")
    private String sendEmail;
    @Value("${email.url}")
    private String url;
    @Value("${email.password}")
    private String password;


    @Autowired
    private MailVerifyMapper mailVerifyMapper;

    @Autowired
    private IUserInfoService userInfoService;

    public int insert(MailVerify record) {
        return mailVerifyMapper.insert(record);
    }

    public MailVerify selectByKey(String uuid) {
        return mailVerifyMapper.selectByKey(uuid);
    }

    public void sendEmail(String email) {
        //如果没有绑定过邮箱就可以发送邮件
        UserInfo current = userInfoService.getCurrent();
        try {
            if (!current.getIsBindEmail()) {
                MailVerify mv = new MailVerify();
                String uuid = UUID.randomUUID().toString();
                mv.setSendTime(new Date());
                mv.setEmail(email);
                mv.setUuid(uuid);
                mv.setUserinfoId(current.getId());
                mailVerifyMapper.insert(mv);

                StringBuilder sb = new StringBuilder(100);
                JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
                //设定mail server
                senderImpl.setHost(host);
                //建立邮件消息
                MimeMessage mailMessage = senderImpl.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
                //设置收件人
                messageHelper.setTo(email);
                messageHelper.setFrom(sendEmail);
                messageHelper.setSubject("邮箱验证");

                //true 表示启动html格式的邮件
                sb.append("点击 <a href='").append(url).append("checkmail.do").append("?key=").append(uuid)
                        .append("'>这里</a>验证");
                messageHelper.setText(sb.toString(), true);
                senderImpl.setUsername(username);
                senderImpl.setPassword(password);
                Properties prop = new Properties();
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.timeout", "25000");
                senderImpl.setJavaMailProperties(prop);
                //发送邮件
                senderImpl.send(mailMessage);
                System.out.println(sb.toString());
                return;
            }
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("发送失败");
        }
    }

    /**
     * 绑定邮箱操作
     *
     * @param uuid
     */
    public void bindEmail(String uuid) {
        MailVerify mv = mailVerifyMapper.selectByKey(uuid);
        if (mv != null) {
            UserInfo userInfo = userInfoService.get(mv.getUserinfoId());
            if (!userInfo.getIsBindEmail()
                    && (DateUtil.getBetweenSeconds(new Date(), mv.getSendTime()) <= BidConst.VERIFYEMAIL_VALID_TIME)) {
                userInfo.setEmail(mv.getEmail());
                userInfo.addState(BitStatesUtils.OP_BIND_EMAIL);
                userInfoService.update(userInfo);
                return;
            }
        }
        throw new RuntimeException("绑定失败,请重新绑定");
    }
}
