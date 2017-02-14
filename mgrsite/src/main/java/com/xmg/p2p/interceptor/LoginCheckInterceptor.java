package com.xmg.p2p.interceptor;

import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台登陆检查的拦截器
 * Created by Panda on 2016/10/30.
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            //如果当前访问的方法没有RequiredLogin标签但是当前没有用户登录,转到登陆界面
            if (hm.getMethodAnnotation(RequireLogin.class) == null
                    && UserContext.getCurrent() == null) {
                response.sendRedirect("/login.html");
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }
}
