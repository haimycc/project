package com.xmg.p2p.listener;

import com.xmg.p2p.base.service.ILogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Panda on 2016/10/30.
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private ILogininfoService logininfoService;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            logininfoService.initAdmin();
    }
}
