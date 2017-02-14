package com.xmg.p2p.base.query;

import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Panda on 2016/11/3.
 */
@Getter@Setter
public class AuthQueryObject extends QueryObject {
    private int state = -1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public Date getBeginDate() {
        return beginDate != null ? DateUtil.getEndDate(beginDate) : null;
    }

    public Date getEndDate() {
        return endDate != null ? DateUtil.getEndDate(endDate) : null;
    }
}
