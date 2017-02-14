package com.xmg.p2p.base.util;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 *用户上下文
 */
public class UserContext {

    public static final String USER_IN_SESSION = "logininfo";
    public static final String VERIFYCODE_IN_SESSION = "verifycode_in_session";

    private static HttpSession getSession() {
        return ((ServletRequestAttributes)   RequestContextHolder
                .getRequestAttributes()).getRequest().getSession();
    }
    /**
     * 添加当前用户
     * @param current
     */
    public static void setCurrent(Logininfo current) {
        getSession().setAttribute(USER_IN_SESSION, current);
    }

    /**
     * 得到当前登陆用户
     * @return
     */
    public static Logininfo getCurrent() {
        return (Logininfo) getSession().getAttribute(USER_IN_SESSION);
    }

    /**
     * 添加一个验证码对象
     */
    public static void setVerifyCode(VerifyCodeVO vo) {
        getSession().setAttribute(VERIFYCODE_IN_SESSION, vo);
    }

    /**
     * 获取验证码对象
     */
    public static VerifyCodeVO getVerifyCode() {
        return (VerifyCodeVO) getSession().getAttribute(VERIFYCODE_IN_SESSION);
    }
}
