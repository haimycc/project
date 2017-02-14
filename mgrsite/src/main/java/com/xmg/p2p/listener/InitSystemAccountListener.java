package com.xmg.p2p.listener;

import com.xmg.p2p.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 系统账户监听器
 * Created by Panda on 2016/11/8.
 */
@Component
public class InitSystemAccountListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private ISystemAccountService systemAccountService;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        systemAccountService.init();
    }
}
