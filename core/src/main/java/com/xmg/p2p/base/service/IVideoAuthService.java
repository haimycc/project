package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VideoAuthQueryObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Panda on 2016/11/3.
 */
public interface IVideoAuthService {

    int insert(VideoAuth record);

    VideoAuth selectByPrimaryKey(Long id);

    PageResult query(VideoAuthQueryObject qo);

    void audit(Long loginInfoValue, String remark, int state);

    List<Map<String,Object>> autoComplate(String keyword);
}
