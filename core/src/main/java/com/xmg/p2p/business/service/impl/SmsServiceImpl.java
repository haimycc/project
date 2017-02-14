package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.business.event.RealAuthSuccessEvent;
import com.xmg.p2p.business.service.ISmsService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Created by Panda on 2016/11/10.
 */
@Service
public class SmsServiceImpl implements ISmsService,ApplicationListener<ApplicationEvent>{

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof RealAuthSuccessEvent) {
            RealAuthSuccessEvent e = (RealAuthSuccessEvent) event;
            sendSms(e.getRealAuth());
        }
    }

    private void sendSms(RealAuth realAuth) {
        System.out.println("用户" + realAuth.getUsername() + "认证成功");

    }
}
