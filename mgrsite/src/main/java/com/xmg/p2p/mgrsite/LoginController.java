package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.RequireLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Panda on 2016/10/30.
 */
@Controller
public class LoginController {
    @Autowired
    private ILogininfoService logininfoService;

    /**
     * 后台用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(String username, String password, HttpServletRequest request) {
        JSONResult json = new JSONResult();
        Logininfo li = logininfoService.login(username, password, request.getRemoteAddr(), Logininfo.ROLE_MANAGER);
        if (li == null) {
            json.setSuccess(false);
            json.setMsg("用户名或者密码错误");
        }
        return json;
    }

    /**
     * 后台首页
     */
    @RequestMapping("index")
    public String index() {
        return "main";
    }
}
