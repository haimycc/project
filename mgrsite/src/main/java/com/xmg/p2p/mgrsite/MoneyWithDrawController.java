package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.query.MoneyWithDrawQueryObject;
import com.xmg.p2p.business.service.IMoneyWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台提现审核
 * Created by Panda on 2016/11/9.
 */
@Controller
public class MoneyWithDrawController {
    @Autowired
    private IMoneyWithdrawService moneyWithdrawService;

    @RequestMapping("moneyWithdraw")
    public String moneyWithDraw(Model model, @ModelAttribute("qo") MoneyWithDrawQueryObject qo) {
        model.addAttribute("pageResult", moneyWithdrawService.query(qo));
        return "moneywithdraw/list";
    }

    @RequestMapping("moneyWithdraw_audit")
    @ResponseBody
    public JSONResult audit(Long id, String remark, int state) {
        moneyWithdrawService.audit(id, remark, state);
        return new JSONResult();
    }

}
