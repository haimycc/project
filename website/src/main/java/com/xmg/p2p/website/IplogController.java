package com.xmg.p2p.website;

import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.service.IIplogService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前天用户登录日志
 * Created by Panda on 2016/10/29.
 */
@Controller
public class IplogController {
    @Autowired
    private IIplogService iplogService;

    @RequestMapping("ipLog")
    public String ipLogList(Model model, @ModelAttribute("qo") IpLogQueryObject qo) {
        qo.setUsername(UserContext.getCurrent().getUsername());
        model.addAttribute("pageResult", iplogService.query(qo));
        return "iplog_list";
    }
}
