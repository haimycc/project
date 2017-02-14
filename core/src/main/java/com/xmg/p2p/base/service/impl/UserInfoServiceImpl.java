package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.mapper.UserInfoMapper;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Panda on 2016/10/28.
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private IVerifyCodeService verifyCodeService;

    public void update(UserInfo userInfo) {
        int ret = userInfoMapper.updateByPrimaryKey(userInfo);
        if (ret < 0) {
            throw new RuntimeException("乐观锁失败" + userInfo.getId());
        }
    }

    @Override
    public void save(UserInfo ui) {
        userInfoMapper.insert(ui);
    }

    @Override
    public UserInfo get(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserInfo getCurrent() {
        return get(UserContext.getCurrent().getId());
    }

    @Override
    public void bindPhone(String phoneNumber, String verifyCode) {
        //判定是否合法
        UserInfo current = getCurrent();
        System.out.println(current.getIsBindPhone());
        System.out.println(verifyCode);
        System.out.println(verifyCodeService.vaild(phoneNumber, verifyCode));
        if (!current.getIsBindPhone()//没有绑定手机
                && verifyCodeService.vaild(phoneNumber, verifyCode)) {//2.验证码合法
            //判定成功
            //1.给用户添加状态码
            System.out.println(11);
            current.addState(BitStatesUtils.OP_BIND_PHONE);
            //给用户设置手机号
            current.setPhoneNumber(phoneNumber);
            //3.修改用户
            update(current);
        } else {
            throw new RuntimeException("绑定失败");
        }
    }

    public void saveBasicInfo(UserInfo userinfo) {
        //得到当前的userinfo
        UserInfo current = this.getCurrent();
        //把参数设置到userinfo中
        current.setEducationBackground(userinfo.getEducationBackground());
        current.setHouseCondition(userinfo.getHouseCondition());
        current.setIncomeGrade(userinfo.getIncomeGrade());
        current.setKidCount(userinfo.getKidCount());
        current.setMarriage(userinfo.getMarriage());
        //给用户添加基本信息的状态吗
        if (!current.getIsBasicInfo()) {
            current.addState(BitStatesUtils.OP_BASIC_INFO);
            //修改
        }
            this.update(current);
    }
}
