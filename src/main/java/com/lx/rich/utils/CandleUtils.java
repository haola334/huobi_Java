package com.lx.rich.utils;

import com.huobi.client.model.Candlestick;
import com.lx.rich.model.CandleDetail;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 07 September 2020
 */
public class CandleUtils {

	public static CandleDetail toCandleDetail(Candlestick candlestick) {
		CandleDetail candleDetail = new CandleDetail();
		candleDetail.setAmount(candlestick.getAmount());
		candleDetail.setClose(candlestick.getClose());
		candleDetail.setHigh(candlestick.getHigh());
		candleDetail.setLow(candlestick.getLow());
		candleDetail.setOpen(candlestick.getOpen());
		candleDetail.setCount(candlestick.getCount());
		candleDetail.setTimestamp(candlestick.getTimestamp());
		candleDetail.setVolume(candlestick.getVolume());

		return candleDetail;
	}
}
