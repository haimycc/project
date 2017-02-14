package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.mapper.VideoAuthMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VideoAuthQueryObject;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.service.IVideoAuthService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Panda on 2016/11/3.
 */
@Service
public class VideoAuthServiceImpl implements IVideoAuthService {
    @Autowired
    private VideoAuthMapper videoAuthMapper;
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private LogininfoMapper logininfoMapper;

    public int insert(VideoAuth record) {
        return videoAuthMapper.insert(record);
    }

    public VideoAuth selectByPrimaryKey(Long id) {
        return videoAuthMapper.selectByPrimaryKey(id);
    }

    public PageResult query(VideoAuthQueryObject qo) {
        int totalCount = videoAuthMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<RealAuth> listData = videoAuthMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(Long loginInfoValue, String remark, int state) {
        //先拿到审核对象
        UserInfo userInfo = userInfoService.get(loginInfoValue);
        //如果没有视频认证就进行认证
        //logininfo的id和userinfo一样,是一起创建的
        //state只有1 和 2的结果
        if (userInfo != null && !userInfo.getIsVedioAuth()) {
            VideoAuth va = new VideoAuth();
            va.setRemark(remark);
            va.setAuditor(UserContext.getCurrent());
            va.setAuditTime(new Date());
            va.setApplyTime(new Date());
            Logininfo li = new Logininfo();
            li.setId(loginInfoValue);
            va.setApplier(li);
            va.setState(state);
            videoAuthMapper.insert(va);
            //给用户添加一个审核状态
            if (state == VideoAuth.STATE_AUDIT) {
                userInfo.addState(BitStatesUtils.OP_VEDIO_AUTH);
                userInfoService.update(userInfo);
            }
        }
    }

    public List<Map<String, Object>> autoComplate(String keyword) {
        return logininfoMapper.autoComplate(keyword);
    }
}
