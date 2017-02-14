package com.xmg.p2p.website;

import com.xmg.p2p.base.service.IMailVerifyService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台发送邮件
 * Created by Panda on 2016/10/31.
 */
@Controller
public class SendEmailController {
    @Autowired
    private IMailVerifyService mailVerifyService;

    /**
     * 发送邮件验证
     * @param email
     * @return
     */
    @RequestMapping("sendEmail")
    @ResponseBody
    public JSONResult sendEmail(String email) {
        JSONResult json = new JSONResult();
        try {
            mailVerifyService.sendEmail(email);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;
    }
}
