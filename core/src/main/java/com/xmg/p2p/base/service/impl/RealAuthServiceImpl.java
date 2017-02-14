package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.mapper.RealAuthMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.event.RealAuthSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Panda on 2016/11/2.
 */
@Service
public class RealAuthServiceImpl implements IRealAuthService {
    @Autowired
    private RealAuthMapper realAuthMapper;

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ApplicationContext ctx;

    public int insert(RealAuth record) {
        return realAuthMapper.insert(record);
    }

    public RealAuth selectByPrimaryKey(Long id) {
        return realAuthMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(RealAuth record) {
        return realAuthMapper.updateByPrimaryKey(record);
    }

    /**
     * 保存实名认证资料
     *
     * @param ra
     */
    public void apply(RealAuth ra) {
        UserInfo current = userInfoService.getCurrent();
        //没有实名认证才填充资料
        if (!current.getIsRealAuth() && current.getRealAuthId() == null) {
            //取得reanlauth对象,并填充值
            RealAuth r = new RealAuth();
            r.setAddress(ra.getAddress());
            r.setApplier(UserContext.getCurrent());
            r.setApplyTime(new Date());
            r.setBornDate(ra.getBornDate());
            r.setIdNumber(ra.getIdNumber());
            r.setImage1(ra.getImage1());
            r.setImage2(ra.getImage2());
            r.setRealName(ra.getRealName());
            r.setSex(ra.getSex());
            r.setState(RealAuth.STATE_NORMAL);
            realAuthMapper.insert(r);
            current.setRealAuthId(r.getId());
            userInfoService.update(current);
        }
    }

    public PageResult query(RealAuthQueryObject qo) {
        int totalCount = realAuthMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<RealAuth> listData = realAuthMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(Long id, String remark, int state) {
        //得到实名认证对象
        RealAuth ra = realAuthMapper.selectByPrimaryKey(id);
        if (ra != null) {
            UserInfo userInfo = userInfoService.get(ra.getApplier().getId());
            //审核通过
            ra.setRemark(remark);
            ra.setAuditTime(new Date());
            ra.setAuditor(UserContext.getCurrent());
            if (state == RealAuth.STATE_AUDIT && !userInfo.getIsRealAuth()) {
                ra.setState(state);
                userInfo.setRealName(ra.getRealName());
                userInfo.setIdNumber(ra.getIdNumber());
                userInfo.addState(BitStatesUtils.OP_REAL_AUTH);
                // 发布一个实名认证审核通过的消息
                ctx.publishEvent(new RealAuthSuccessEvent(this,ra));
            } else {
                ra.setState(state);
                userInfo.setRealAuthId(null);
            }
            realAuthMapper.updateByPrimaryKey(ra);
            userInfoService.update(userInfo);
        }
    }
}
