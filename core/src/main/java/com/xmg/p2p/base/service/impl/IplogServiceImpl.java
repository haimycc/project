package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Iplog;
import com.xmg.p2p.base.mapper.IplogMapper;
import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Panda on 2016/10/29.
 */
@Service
public class IplogServiceImpl implements IIplogService {
    @Autowired
    private IplogMapper iplogMapper;
    @Override
    public PageResult query(IpLogQueryObject qo) {
        int totalCount = iplogMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<Iplog> listData = iplogMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(),qo.getPageSize());
    }
}
