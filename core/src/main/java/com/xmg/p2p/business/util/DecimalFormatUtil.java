package com.xmg.p2p.business.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.xmg.p2p.base.util.BidConst;

/**
 * 数字格式化工具类
 * */
public class DecimalFormatUtil {
	// 金额
	public static BigDecimal amountFormat(BigDecimal number) {
		number = number.setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP);
		return number;
	}

	// 利率
	public static BigDecimal rateFormat(BigDecimal number) {
		number = number.setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP);
		return number;
	}

	public static BigDecimal decimalRateFormat(BigDecimal number) {
		return number.multiply(BigDecimal.valueOf(100));
	}

	// 月利率
	public static BigDecimal monthRateFormat(BigDecimal number) {
		return number.multiply(BigDecimal.valueOf(100)).divide(
				BigDecimal.valueOf(12), BidConst.CAL_SCALE,
				RoundingMode.HALF_UP);
	}

	public static BigDecimal formatBigDecimal(BigDecimal data, int scal) {
		if (null == data)
			return new BigDecimal(0.00);
		return data.setScale(scal, BigDecimal.ROUND_HALF_UP);
	}
}
