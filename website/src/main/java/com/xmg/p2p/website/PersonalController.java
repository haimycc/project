package com.xmg.p2p.website;

import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IMailVerifyService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

/**
 * Created by Panda on 2016/10/28.
 */
@Controller
public class PersonalController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IMailVerifyService mailVerifyService;
    @Autowired
    private ServletContext servletContext;


    @RequireLogin
    @RequestMapping("personal")
    public String personalCenter(Model model) {
        model.addAttribute("account", accountService.getCurrent());
        model.addAttribute("userinfo", userInfoService.getCurrent());
        return "personal";
    }


    /**
     * 完成手机绑定
     */
    @RequestMapping("bindPhone")
    @ResponseBody
    public JSONResult bindPhone(String phoneNumber, String verifyCode) {
        JSONResult json = new JSONResult();
        try {
            userInfoService.bindPhone(phoneNumber, verifyCode);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }

    /**
     * 邮箱绑定
     */
    /**
     * 邮箱验证
     * Created by Panda on 2016/10/31.
     */
    @RequestMapping("bindEmail")
    public String bindEmail(String key, Model model) {
        try {
            mailVerifyService.bindEmail(key);
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("msg", e.getMessage());
        }
        return "checkmail_result";
    }

    @RequestMapping("personal_img")
    @ResponseBody
    public String changeImg(MultipartFile image) {
        String fileName = UploadUtil.upload(image, servletContext.getRealPath("/images"));
        UserInfo current = userInfoService.getCurrent();
        current.setImage("/images/" + fileName);
        userInfoService.update(current);
        return "/images/" + fileName;
    }

    @RequestMapping("logout")
    public String logout() {
        return "redirect:/login.html";
    }

}

