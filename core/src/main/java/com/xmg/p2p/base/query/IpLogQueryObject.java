package com.xmg.p2p.base.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 登陆日志查询对象
 * Created by Panda on 2016/10/29.
 */
@Getter
@Setter
public class IpLogQueryObject extends QueryObject {
    private int state = -1;
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss",timezone="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss",timezone="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String username;
    private int userType = -1;

    public Date getEndDate() {
        return endDate != null ? DateUtil.getEndDate(endDate) : null;
    }

    public String getUsername() {
        return StringUtils.hasLength(username) ? username : null;
    }
}
