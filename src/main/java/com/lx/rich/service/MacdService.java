package com.lx.rich.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.model.Macd;
import com.lx.rich.utils.ToolUtils;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 16 September 2020
 */
public class MacdService {

	public static final int shortPeriod = 12;
	public static final int longPeriod = 26;
	public static final int midPeriod = 9;

	private static final int ONE_MINUTE = 60 * 1000;

	private static Map<Long, Macd> macdCache = Maps.newConcurrentMap();

	private static Supplier<MacdService> supplier = Suppliers.memoize(()-> new MacdService());

	private MacdService() {

	}

	public static MacdService getInstance() {
		return supplier.get();
	}


	public void buildCache(List<Candlestick> candlesticks) {

		//小于longPeriod的不处理，因为算不出来
		if (candlesticks.size() < longPeriod) {
			return;
		}

		for (int i = longPeriod - 1; i < candlesticks.size(); i++) {
			List<Candlestick> subList = candlesticks.subList(i - longPeriod + 1, i + 1);
			Macd macd = ToolUtils.getMACD(subList, shortPeriod, longPeriod, midPeriod);

			macdCache.put(candlesticks.get(i).getTimestamp(), macd);
		}

	}

	public BigDecimal totalMacd(Candlestick from, Candlestick to) {

		long currTime = from.getTimestamp();

		BigDecimal result = new BigDecimal(0);

		while (true) {
			if (currTime > to.getTimestamp()) {
				break;
			}
			Macd macd = macdCache.get(currTime);
			if (macd == null) {
				System.out.println("发现macd为null的时间点，timestamp： " + new Date(currTime));
				continue;
			}

			result.add(macd.getMacd());
			currTime+=ONE_MINUTE;
		}

		return result;
	}

}
