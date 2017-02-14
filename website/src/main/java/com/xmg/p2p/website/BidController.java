package com.xmg.p2p.website;

import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * Created by Panda on 2016/11/7.
 */
@Controller
public class BidController {
    @Autowired
    private IBidRequestService bidRequestService;

    /**
     * 投资
     */
    @RequestMapping("borrow_bid")
    @ResponseBody
    public JSONResult bid(Long bidRequestId, BigDecimal amount) {
        bidRequestService.bid(bidRequestId, amount);
        return new JSONResult();
    }
}
