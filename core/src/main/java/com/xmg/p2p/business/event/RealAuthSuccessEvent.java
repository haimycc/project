package com.xmg.p2p.business.event;

import com.xmg.p2p.base.domain.RealAuth;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Created by Panda on 2016/11/10.
 */
@Getter
public class RealAuthSuccessEvent extends ApplicationEvent{
    /**
     * 事件相关对象
     * @param source
     */
    private RealAuth realAuth;
    public RealAuthSuccessEvent(Object source, RealAuth realAuth) {
        super(source);
        this.realAuth = realAuth;
    }
}
