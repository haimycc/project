package com.xmg.p2p.website;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册相关控制器
 */
@Controller
public class RegisterController {
    @Autowired
    private ILogininfoService logininfoService;

    /**
     * 注册功能
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("register")
    @ResponseBody
    public JSONResult register(String username, String password) {
        JSONResult json = new JSONResult();
        try {
            logininfoService.register(username, password);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;
    }

    /**
     * 注册检查用户名是否重复
     *
     * @param username
     * @return
     */
    @RequestMapping("checkUsername")
    @ResponseBody
    public boolean checkUsername(String username) {
        return logininfoService.checkUsername(username);
    }

    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(String username, String password, HttpServletRequest request) {
        JSONResult json = new JSONResult();
        Logininfo current = logininfoService.login(username, password, request.getRemoteAddr(), Logininfo.ROLE_USER);
        if (current == null) {
            json.setMsg("用户名或密码错误");
            json.setSuccess(false);
        }

        return json;
    }
}
