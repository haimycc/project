package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.service.IIplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台登陆日志
 * Created by Panda on 2016/10/30.
 */
@Controller
public class IplogController {
    @Autowired
    private IIplogService iplogService;

    @RequestMapping("ipLog")
    public String ipLogList() {
        return "ipLog/list";
    }

    @RequestMapping("iplog_list")
    public String iplogList(@ModelAttribute("qo")IpLogQueryObject qo, Model model) {
        model.addAttribute("pageReSULT", iplogService.query(qo));
        return "ipLog/json_list";
    }
}
