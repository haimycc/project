package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Panda on 2016/11/4.
 */
@Controller
public class UserFileAuthController {
    @Autowired
    private IUserFileService userFileService;

    @RequestMapping("userFileAuth")
    public String userFileAuth(Model model, @ModelAttribute("qo")UserFileQueryObject qo) {
        model.addAttribute("pageResult", userFileService.query(qo));
        return "userFileAuth/list";
    }

    @RequestMapping("userFile_audit")
    @ResponseBody
    public JSONResult audit(Long id, String remark,int score,int state) {
        userFileService.audit(id,remark,score,state);
        return new JSONResult();
    }
}
