package com.xmg.p2p.website;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

/**
 * Created by Panda on 2016/11/2.
 */
@Controller
public class RealAuthController {
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ServletContext servletContext;


    @RequestMapping("realAuth")
    public String realAuth(Model model) {
        UserInfo current = userInfoService.getCurrent();
        if (current.getRealAuthId() == null) {//没有认证 state 2
            return "realAuth";
        } else {
            //已经认证
            if (current.getIsRealAuth()) {//state 1
                //1.验证通过
                RealAuth realAuth = realAuthService.selectByPrimaryKey(current.getRealAuthId());
                model.addAttribute("realAuth", realAuth);
                model.addAttribute("auditing", false);
            } else {//state 0  不用判断state 2, 如果state==2 current 里面没有realAuthId
                //2.审核中
                model.addAttribute("auditing", true);
            }
                return "realAuth_result";
        }
    }

    @RequestMapping("realAuth_save")
    public String realAuthApply(RealAuth realAuth, Model model) {
        realAuthService.apply(realAuth);
        model.addAttribute("auditing", true);
        return "realAuth_result";
    }

    /**
     * 上传图片
     */
    @RequestMapping("upload")
    @ResponseBody
    public String upload(MultipartFile image) {
        String fileName = UploadUtil.upload(image, servletContext.getRealPath("/upload"));
        return "/upload/" + fileName;
    }
}
