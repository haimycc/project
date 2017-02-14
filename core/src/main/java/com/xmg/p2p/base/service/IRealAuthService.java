package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;

/**
 * Created by Panda on 2016/11/2.
 */
public interface IRealAuthService {
    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

    /**
     * 保存实名认证资料
     * @param realAuth
     */
    void apply(RealAuth realAuth);

    /**
     * 高级查询及分页,申请的对象
     * @param qo
     * @return
     */
    PageResult query(RealAuthQueryObject qo);

    /**
     * 审核
     * @param remark
     * @param state
     */
    void audit(Long id,String remark,int state);
}
