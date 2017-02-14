package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.query.VideoAuthQueryObject;

import java.util.List;
import java.util.Map;

public interface VideoAuthMapper {

    int insert(VideoAuth record);

    VideoAuth selectByPrimaryKey(Long id);

    int queryForCount(VideoAuthQueryObject qo);

    List<RealAuth> query(VideoAuthQueryObject qo);

}