package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.query.VideoAuthQueryObject;
import com.xmg.p2p.base.service.IVideoAuthService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 视频认证对象
 * Created by Panda on 2016/11/3.
 */
@Controller
public class VideoAuthController {

    @Autowired
    private IVideoAuthService videoAuthService;

    @RequestMapping("vedioAuth")
    public String vedioAuth(Model model, @ModelAttribute("qo")VideoAuthQueryObject qo) {
        model.addAttribute("pageResult", videoAuthService.query(qo));
        return "vedioAuth/list";
    }

    @RequestMapping("vedioAuth_audit")
    @ResponseBody
    public JSONResult vedioAuth_audit(Long loginInfoValue, String remark, int state) {
        videoAuthService.audit(loginInfoValue, remark, state);
        return new JSONResult();
    }


    @RequestMapping("autocomplate")
    @ResponseBody
    public List<Map<String, Object>> autoComplate(String keyword) {
        return videoAuthService.autoComplate(keyword);
    }
}
