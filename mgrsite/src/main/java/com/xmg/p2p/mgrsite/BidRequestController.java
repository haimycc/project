package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Panda on 2016/11/7.
 */
@Controller
public class BidRequestController {
    @Autowired
    private IBidRequestService bidRequestService;

    /**
     * 一审列表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("bidrequest_audit1_list")
    public String audit1List(Model model, @ModelAttribute("qo")BidRequestQueryObject qo) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "bidrequest/audit1";
    }

    /**
     * 满标一审
     */
    @RequestMapping("bidrequest_audit1")
    @ResponseBody
    public JSONResult audit1(Long id,String remark,int state) {
        bidRequestService.audit1(id, remark, state);
        return new JSONResult();
    }

    /**
     * 二审列表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("bidrequest_audit2_list")
    public String audit2List(Model model, @ModelAttribute("qo")BidRequestQueryObject qo) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "bidrequest/audit2";
    }

    /**
     * 满标二审
     */
    @RequestMapping("bidrequest_audit2")
    @ResponseBody
    public JSONResult audit2(Long id,String remark,int state) {
        bidRequestService.audit2(id, remark, state);
        return new JSONResult();
    }
}
