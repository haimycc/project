package com.xmg.p2p.website;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 借款相关
 * Created by Panda on 2016/10/31.
 */
@Controller
public class BorrowController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserFileService userFileService;


    @RequestMapping("borrow")
    public String borrowIndex(Model model) {
        Logininfo current = UserContext.getCurrent();
        if (current == null) {
            //没有登陆
            return "redirect:/borrow.html";
        } else {
            model.addAttribute("account", accountService.getCurrent());
            model.addAttribute("userinfo", userInfoService.getCurrent());
            model.addAttribute("creditBorrowScore", BidConst.BORROW_MIN_SCORE);
            return "borrow";
        }
    }

    @RequestMapping("borrowInfo")
    public String borrowInfo(Model model) {
        UserInfo current = userInfoService.getCurrent();
        if (current.getHasBidRequestInProcess()) {
            return "borrow_apply_result";
        }
        Account account = accountService.getCurrent();
        model.addAttribute("minBidRequestAmount", BidConst.SMALLEST_BIDREQUEST_AMOUNT);
        model.addAttribute("minBidAmount", BidConst.SMALLEST_BID_AMOUNT);
        model.addAttribute("account", account);
        return "borrow_apply";
    }

    @RequestMapping("borrow_apply")
    public String borrowApply(BidRequest bidRequest) {
        UserInfo current = userInfoService.getCurrent();
        //先判断该用户是否具备贷款的资格
        //1.没有在借贷流程中
        //2.四项资料都满足
        if (current.getHasBidRequest()) {
            //如果条件满足,但是已经申请过,那么就跳到待审核界面
            if (current.getHasBidRequestInProcess()) {
                return "borrow_apply_result";
            }
            //如果具备,再调用申请方法
            bidRequestService.apply(bidRequest);
            return "borrow_apply_result";
        }
        //不满足资格,就跳到申请借款界面
        return "redirect:borrow.do";
    }

    @RequestMapping("invest")
    public String invest(Model model) {
        return "invest";
    }

    @RequestMapping("invest_list")
    public String investList(Model model, @ModelAttribute BidRequestQueryObject qo) {
        if (qo.getBidRequestState() == -1) {
            qo.setStates(new int[]{BidConst.BIDREQUEST_STATE_BIDDING, BidConst.BIDREQUEST_STATE_PAYING_BACK, BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        }
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "invest_list";
    }

    @RequestMapping("borrow_info")
    public String borrowInfoList(Model model, Long id) {
        BidRequest bidRequest = bidRequestService.get(id);
        //借款对象不为空
        if (bidRequest != null) {
            //所有用户都能看到的公共信息
            UserInfo userInfo = userInfoService.get(bidRequest.getCreateUser().getId());
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("account", accountService.get(UserContext.getCurrent().getId()));
            model.addAttribute("userFiles", userFileService.getSelectList(userInfo.getId(), true));
            model.addAttribute("realAuth", realAuthService.selectByPrimaryKey(userInfo.getRealAuthId()));
            model.addAttribute("bidRequest", bidRequest);
            //如果登录用户就是自己
            if (userInfo.getId() == UserContext.getCurrent().getId()) {
                model.addAttribute("self", true);
            } else {
                model.addAttribute("self", false);
            }
        }
        return "borrow_info";
    }


}

