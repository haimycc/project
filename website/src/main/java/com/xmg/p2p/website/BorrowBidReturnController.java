package com.xmg.p2p.website;

import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.query.PaymentScheduleQueryObject;
import com.xmg.p2p.business.service.IPaymentScheduleServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Panda on 2016/11/9.
 */
@Controller
public class BorrowBidReturnController {

    @Autowired
    private IPaymentScheduleServcie paymentScheduleServcie;
    @Autowired
    private IAccountService accountService;

    @RequestMapping("borrowBidReturn_list")
    public String borrowBidReturnList(@ModelAttribute("qo")PaymentScheduleQueryObject qo,Model model) {
        model.addAttribute("pageResult", paymentScheduleServcie.query(qo));
        model.addAttribute("account", accountService.getCurrent());
        return "returnmoney_list";
    }

    @RequestMapping("returnMoney")
    @ResponseBody
    public JSONResult returnMoney(Long id) {
        paymentScheduleServcie.returnMoney(id);
        return new JSONResult();
    }
}
