package com.xmg.p2p.website;

import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.service.IPlatformBankInfoService;
import com.xmg.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Panda on 2016/11/6.
 */
@Controller
public class RechargeController {

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;
    @Autowired
    private IRechargeOfflineService rechargeOfflineService;


    @RequestMapping("recharge")
    public String recharge(Model model) {
        model.addAttribute("banks", platformBankInfoService.list());
        return "recharge";
    }

    @RequestMapping("recharge_save")
    @ResponseBody
    public JSONResult apply(RechargeOffline ro) {
        rechargeOfflineService.apply(ro);
        return new JSONResult();
    }
}
