package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.MoneyWithDraw;
import com.xmg.p2p.business.query.MoneyWithDrawQueryObject;

import java.util.List;

public interface MoneyWithDrawMapper {

    int insert(MoneyWithDraw record);

    MoneyWithDraw selectByPrimaryKey(Long id);

    int updateByPrimaryKey(MoneyWithDraw record);

    int queryForCount(MoneyWithDrawQueryObject qo);

    List<MoneyWithDraw> query(MoneyWithDrawQueryObject qo);
}