package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户信息服务
 * Created by Panda on 2016/10/28.
 */
public interface IUserInfoService {

    /**
     * 处理乐观锁更新的问题
     * 只要更新UserInfo信息就要调用这个方法
     */
    void update(UserInfo userInfo);

    /**
     * 保存
     * @param ui
     */
    void save(UserInfo ui);

    UserInfo get(Long id);

    UserInfo getCurrent();

    /**
     * 执行手机绑定
     * @param phoneNumber
     * @param verifyCode
     */
    void bindPhone(String phoneNumber, String verifyCode);

    /**
     * 保存用户基本资料
     * @param userinfo
     */
    void saveBasicInfo(UserInfo userinfo);


}
