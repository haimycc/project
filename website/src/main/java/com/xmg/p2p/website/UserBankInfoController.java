package com.xmg.p2p.website;

import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.business.domain.UserBankinfo;
import com.xmg.p2p.business.service.IUserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 绑定银行卡操作
 * Created by Panda on 2016/11/9.
 */
@Controller
public class UserBankInfoController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserBankinfoService userBankinfoService;

    @RequestMapping("bankInfo")
    public String userBankInfo(Model model) {
        UserInfo current = userInfoService.getCurrent();
        if (current.getIsBindBank()) {
            model.addAttribute("bankInfo", userBankinfoService.selectByUserId(current.getId()));
            return "bankInfo_result";
        } else {
            model.addAttribute("userinfo", current);
            return "bankInfo";

        }
    }

    @RequestMapping("bankInfo_save")
    public String saveUserBankinfo(UserBankinfo userBankinfo) {
        userBankinfoService.save(userBankinfo);
        return "redirect:/bankInfo.do";
    }
}
