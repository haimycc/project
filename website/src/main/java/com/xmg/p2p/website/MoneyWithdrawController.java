package com.xmg.p2p.website;

import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.service.IMoneyWithdrawService;
import com.xmg.p2p.business.service.IUserBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 前台提现
 * Created by Panda on 2016/11/9.
 */
@Controller
public class MoneyWithdrawController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserBankinfoService userBankinfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IMoneyWithdrawService moneyWithdrawService;

    @RequestMapping("moneyWithdraw")
    public String moneyWithdraw(Model model) {
        UserInfo current = userInfoService.getCurrent();
        model.addAttribute("haveProcessing", current.getHasWithDrawInProcess());
        model.addAttribute("account", accountService.getCurrent());
        model.addAttribute("bankInfo", userBankinfoService.selectByUserId(current.getId()));
        return "moneyWithdraw_apply";
    }

    @RequestMapping("moneyWithdraw_apply")
    @ResponseBody
    public JSONResult apply(BigDecimal moneyAmount) {
        moneyWithdrawService.apply(moneyAmount);
        return new JSONResult();
    }

}
