package com.xmg.p2p.website;

import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用于发送验证码
 * Created by Panda on 2016/10/31.
 */
@Controller
public class SendVerifyCodeController {
    @Autowired
    private IVerifyCodeService verifyCodeService;

    /**
     * 发送手机验证码
     * @param phoneNumber
     * @return
     */
    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber) {
        JSONResult json = new JSONResult();
        try {
            verifyCodeService.sendVerifyCode(phoneNumber);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;
    }

}
