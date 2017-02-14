package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Iplog;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.mapper.IplogMapper;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.MD5;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Panda on 2016/10/26.
 */
@Service
public class LogininfoServiceImpl implements ILogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IplogMapper iplogMapper;

    public Logininfo register(String username, String password) {
        //1.是否存在相同用户名
        //返回计数 作为一个判断标记
        int count = logininfoMapper.countByUsername(username);
        //2.如果存在 抛出异常
        if (count > 0) {
            throw new RuntimeException("该用户名已存在");
        } else {
            //3.如果不存在,就保存该用户信息
            Logininfo li = new Logininfo();
            li.setUsername(username);
            li.setPassword(MD5.encode(password));
            li.setState(Logininfo.STATE_NORMAL);
            li.setUserType(Logininfo.ROLE_USER);
            logininfoMapper.insert(li);

            //初始化用户信息
            UserInfo ui = new UserInfo();
            ui.setId(li.getId());
            userInfoService.save(ui);

            //初始化用户账户信息
            Account account = new Account();
            account.setId(li.getId());
            accountService.save(account);
            return li;
        }
    }


    public boolean checkUsername(String username) {
        return logininfoMapper.countByUsername(username) <= 0;
    }

    public Logininfo login(String username, String password, String ip, int userType) {
        Logininfo li = logininfoMapper.login(username, MD5.encode(password), userType);
        Iplog log = new Iplog();
        log.setLoginTime(new Date());
        log.setUsername(username);
        log.setIp(ip);
        log.setUserType(userType);
        if (li != null) {
            //登陆成功,把用户放到session中
            UserContext.setCurrent(li);
            log.setState(Iplog.LOGIN_SUCCESS);
        } else {
            log.setState(Iplog.LOGIN_FAILED);
        }
        iplogMapper.insert(log);
        return li;
    }

    @Override
    public void initAdmin() {
        //检查系统是否有管理源
        int count = logininfoMapper.countByuserType(Logininfo.ROLE_MANAGER);
        //如果没有,创建一个默认的,并添加
        if (count <= 0) {
            Logininfo admin = new Logininfo();
            admin.setUsername(BidConst.DEFAULT_ADMIN_USERNAME);
            admin.setPassword(MD5.encode(BidConst.DEFAULT_ADMIN_password));
            admin.setUserType(Logininfo.ROLE_MANAGER);
            admin.setState(Logininfo.STATE_NORMAL);
            logininfoMapper.insert(admin);
        }
    }

    @Override
    public List<Map<String, Object>> autoComplate(String keyword) {
        return logininfoMapper.autoComplate(keyword);
    }
}
