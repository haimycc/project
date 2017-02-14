package com.xmg.p2p.base.service;

import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.query.PageResult;

/**
 * 登陆日志
 * Created by Panda on 2016/10/29.
 */
public interface IIplogService {

    /**
     * 高级分页查询
     * @param qo
     * @return
     */
    PageResult query(IpLogQueryObject qo);
}
