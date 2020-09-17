package com.lx.rich.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.model.Macd;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 15 September 2020
 */
public class ToolUtils {

	public static final BigDecimal getEXPMA(final List<BigDecimal> list, final int number) {
		// 开始计算EMA值，
		BigDecimal k = new BigDecimal("2.0").divide(new BigDecimal(number + 1) );// 计算出序数
		BigDecimal ema = list.get(0);// 第一天ema等于当天收盘价
		for (int i = 1; i < list.size(); i++) {
			// 第二天以后，当天收盘 收盘价乘以系数再加上昨天EMA乘以系数-1
//			list.get(i) * k + ema * (1 - k)
			ema = list.get(i).multiply(k).add(ema.multiply(new BigDecimal(1).subtract(k)));
		}
		return ema;
	}

	public static final Macd getMACD(final List<Candlestick> list, final int shortPeriod, final int longPeriod, int midPeriod) {
		Macd macd = new Macd();
		List<BigDecimal> diffList = Lists.newArrayList();
		BigDecimal shortEMA = new BigDecimal("0");
		BigDecimal longEMA = new BigDecimal("0");
		BigDecimal dif = new BigDecimal("0");
		BigDecimal dea = new BigDecimal("0");

		List<BigDecimal> closeList = list.stream().map(x -> x.getClose()).collect(Collectors.toList());

		for (int i = list.size() - 1; i >= 0; i--) {
			List<BigDecimal> sublist = closeList.subList(0, list.size() - i);
			shortEMA = getEXPMA(sublist, shortPeriod);
			longEMA = getEXPMA(sublist, longPeriod);
			dif = shortEMA.subtract(longEMA);
			diffList.add(dif);
		}
		dea = getEXPMA(diffList, midPeriod);
		macd.setDif(dif);
		macd.setDea(dea);
		macd.setMacd(dif.subtract(dea).multiply(new BigDecimal(2)));
		return macd;
	}

}
