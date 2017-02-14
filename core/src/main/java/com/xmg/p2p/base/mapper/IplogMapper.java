package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Iplog;
import com.xmg.p2p.base.query.IpLogQueryObject;

import java.util.List;

public interface IplogMapper {

    int insert(Iplog record);


    int queryForCount(IpLogQueryObject qo);

    List<Iplog> query(IpLogQueryObject qo);
}