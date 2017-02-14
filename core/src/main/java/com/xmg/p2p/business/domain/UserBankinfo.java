package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * Created by Panda on 2016/11/9.
 */
@Setter
@Getter
public class UserBankinfo extends BaseDomain{

    private String bankName;
    private String accountName;
    private String accountNumber;
    private String bankForkName;
    private Long userinfoId;
    /**
     * 获取用户真实名字的隐藏字符串，只显示姓氏
     *
     * @param
     *
     * @return
     */

    public String getAnonymousRealName () {
        if (StringUtils.hasLength(this.accountName)) {
            int len = accountName.length();
            String replace = "";
            replace += accountName.charAt(0);
            for (int i = 1; i < len; i++) {
                replace += "*";
            }
            return replace;
        }
        return accountName;
    }

    /**
     * 获取用户身份号码的隐藏字符串
     *
     * @param
     * @return
     */
    public String getAnonymousAccountNumber() {
        if (StringUtils.hasLength(accountNumber)) {
            int len = accountNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += accountNumber.charAt(i);
                }
            }
            return replace;
        }
        return accountNumber;
    }

}
