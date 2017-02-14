package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Panda on 2016/10/28.
 */
@Setter
@Getter
public class UserInfo extends BaseDomain {
    private int version;//版本控制
    private Long bitState = 0L;//用户状态值
    private String realName;//用户实名值
    private String idNumber;//用户身份证号
    private String phoneNumber;//用户电话
    private String email;//邮箱
    private int authScore;//累加风控材料得分
    private String image;

    private SystemDictionaryItem incomeGrade;//收入
    private SystemDictionaryItem marriage;//婚姻情况
    private SystemDictionaryItem kidCount;//子女情况
    private SystemDictionaryItem educationBackground;//学历
    private SystemDictionaryItem houseCondition;//住房条件

    private Long realAuthId;//资料对象的id


    /**
     * 判断用户是否绑定了银行卡
     */
    public boolean getHasWithDrawInProcess() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.HAS_WITHDRAW_IN_PROCESS);
    }


    /**
     * 判断用户是否绑定了银行卡
     */
    public boolean getIsBindBank() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_BIND_BANK);
    }

    /**
     * 判定当前用户是否已经绑定了手机
     */
    public boolean getIsBindPhone() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_BIND_PHONE);
    }

    /**
     * 判定当前用户是否已经绑定了邮箱
     */
    public boolean getIsBindEmail() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_BIND_EMAIL);
    }

    /**
     * 判定当前用户是否实名认证
     */
    public boolean getIsRealAuth() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_REAL_AUTH);
    }

    /**
     * 判定当前用户是否视频认证
     */
    public boolean getIsVedioAuth() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_VEDIO_AUTH);
    }

    /**
     * 判定当前用户是否填写了基本资料
     */
    public boolean getIsBasicInfo() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.OP_BASIC_INFO);
    }

    /**
     * 判定当前用户是否填写了基本资料
     */
    public boolean getHasBidRequestInProcess() {
        return BitStatesUtils.hasState(bitState, BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
    }


    /**
     * 添加状态码
     *
     * @param state
     */
    public void addState(long state) {
        bitState = BitStatesUtils.addState(bitState, state);
    }

    public void removeState(long state) {
        this.setBitState(BitStatesUtils.removeState(this.bitState, state));
    }

    /**
     * 判断用户是否满足四项借款要求
     *
     * @return
     */
    public boolean getHasBidRequest() {
        return getIsBasicInfo()
                && getIsRealAuth()
                && getIsBasicInfo()
                && getIsVedioAuth();
    }
}
