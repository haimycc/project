package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Panda on 2016/11/2.
 */
@Controller
public class RealAuthController {
    @Autowired
    private IRealAuthService realAuthService;

    @RequestMapping("realAuth")
    public String realAuth(Model model, @ModelAttribute("qo") RealAuthQueryObject qo) {
        model.addAttribute("pageResult", realAuthService.query(qo));
        return "realAuth/list";
    }

    @RequestMapping("realauthEidt")
    @ResponseBody
    public RealAuth realauthList(Long id) {
        RealAuth realAuth = realAuthService.selectByPrimaryKey(id);
        return realAuth;
    }

    @RequestMapping("realAuth_audit")
    @ResponseBody
    public JSONResult audit(Long id,String remark,int state) {
        realAuthService.audit(id,remark,state);
        return new JSONResult();
    }
}
