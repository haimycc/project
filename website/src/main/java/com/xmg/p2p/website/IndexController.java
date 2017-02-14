package com.xmg.p2p.website;

import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.util.BidRequestOrderBy;
import com.xmg.p2p.business.util.BidRequestOrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Panda on 2016/11/5.
 */
@Controller
public class IndexController {

    @Autowired
    private IBidRequestService bidRequestService;


    @RequestMapping("index")
    public String index(Model model) {
        //招标>满标>已还清
        BidRequestQueryObject qo = new BidRequestQueryObject();
        qo.setStates(new int[]{BidConst.BIDREQUEST_STATE_BIDDING,BidConst.BIDREQUEST_STATE_PAYING_BACK,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        qo.setPageSize(5);
        qo.setOrderType(BidRequestOrderType.ASC);
        qo.setOrderBy(BidRequestOrderBy.BIDREQUEST_STATE);
        model.addAttribute("bidRequests", bidRequestService.listInvest(qo));
        return "main";
    }
}
