package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Panda on 2016/10/28.
 */
@Getter
@Setter
public class Account extends BaseDomain {
    private String version;
    private String tradePassword;
    private BigDecimal usableAmount = BidConst.ZERO;//账户可用余额
    private BigDecimal freezedAmount = BidConst.ZERO;//账户冻结金额
    private BigDecimal unReceiveInterest = BidConst.ZERO;//账户待收利息
    private BigDecimal unReceivePrincipal = BidConst.ZERO;//账户待收本金
    private BigDecimal unReturnAmount = BidConst.ZERO;//账户待还金额
    private BigDecimal remainBorrowLimit = BidConst.DEFAULT_BORROW_LIMIT;//账户剩余授信额度
    private BigDecimal borrowLimit = BidConst.DEFAULT_BORROW_LIMIT;//账户授信额度

    /**
     * 返回总金额
     * @return
     */
    public BigDecimal getTotalAmount() {
        return usableAmount.add(freezedAmount).add(unReceivePrincipal);
    }
}
