package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;
import com.xmg.p2p.business.service.IPlatformBankInfoService;
import com.xmg.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台账号管理
 * Created by Panda on 2016/11/6.
 */
@Controller
public class RechargeController {
    @Autowired
    private IPlatformBankInfoService platformBankInfoService;
    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    @RequestMapping("companyBank_list")
    public String recharge(Model model, @ModelAttribute("qo")PlatformBankInfoQueryObject qo) {
        model.addAttribute("pageResult", platformBankInfoService.query(qo));
        return "platformbankinfo/list";
    }

    @RequestMapping("companyBank_update")
    @ResponseBody
    public JSONResult saveOrUpdate(PlatformBankInfo pb) {
        platformBankInfoService.saveOrUpdate(pb);
        return new JSONResult();
    }

    @RequestMapping("rechargeOffline")
    public String rechargeOffline(Model model, @ModelAttribute("qo")RechargeOfflineQueryObject qo) {
        model.addAttribute("pageResult",rechargeOfflineService.query(qo));
        model.addAttribute("banks",platformBankInfoService.list());
        return "rechargeoffline/list";
    }

    @RequestMapping("rechargeOffline_audit")
    @ResponseBody
    public JSONResult audit(Long id, String remark,int state) {
        rechargeOfflineService.audit(id, remark, state);
        return new JSONResult();
    }
}
