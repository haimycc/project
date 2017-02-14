package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Logininfo;

import java.util.List;
import java.util.Map;

/**
 * logininfo相关服务
 */
public interface ILogininfoService {
    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    Logininfo register(String username, String password);

    /**
     * 检查注册用户名是否重复
     * 返回true就是没有重复,否则就是重复
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    Logininfo login(String username, String password, String ip, int userType);

    /**
     * 初始化第一个管理员账户
     */
    void initAdmin();

    /**
     * 自动补全
     * @param keyword
     * @return
     */
    List<Map<String,Object>> autoComplate(String keyword);
}
