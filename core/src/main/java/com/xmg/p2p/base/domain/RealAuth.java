package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 实名认证对象
 * Created by Panda on 2016/11/2.
 */
@Getter
@Setter
public class RealAuth extends BaseAuthDomain {
    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;

    private String realName;//实名
    private int sex;
    private String idNumber;//身份证号码
    private String bornDate;//出生日期
    private String address;//地址
    private String image1;//正面
    private String image2;//反面

    public String getSexDisplay() {
        return sex == SEX_MALE ? "男" : "女";
    }
}
