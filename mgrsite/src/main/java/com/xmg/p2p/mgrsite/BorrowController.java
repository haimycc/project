package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Panda on 2016/11/5.
 */
@Controller
public class BorrowController {

    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserFileService userFileService;

    @RequestMapping("bidrequest_publishaudit_list")
    public String bidRequestList(Model model, @ModelAttribute("qo") BidRequestQueryObject qo) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "bidrequest/publish_audit";
    }

    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    public JSONResult bidRequestAudit(Long id, String remark, int state) {
        bidRequestService.audit(id, remark, state);
        return new JSONResult();
    }

    @RequestMapping("borrow_info")
    public String borrowInfo(Model model,Long id) {
        BidRequest bidRequest = bidRequestService.get(id);
        UserInfo userinfo = userInfoService.get(bidRequest.getCreateUser()
                .getId());
        System.out.println((bidRequest.getBids()));
        model.addAttribute("bidRequest", bidRequest);
        model.addAttribute("userInfo", userinfo);
        model.addAttribute("audits",
                bidRequestService.listAuditHistoryByBidRequest(id));
        model.addAttribute("realAuth",
                realAuthService.selectByPrimaryKey(userinfo.getRealAuthId()));
        UserFileQueryObject qo = new UserFileQueryObject();
        qo.setApplierId(userinfo.getId());
        qo.setState(UserFile.STATE_AUDIT);
        qo.setPageSize(-1);
        model.addAttribute("userFiles", this.userFileService.queryForList(qo));
        return "bidrequest/borrow_info";
    }
}
