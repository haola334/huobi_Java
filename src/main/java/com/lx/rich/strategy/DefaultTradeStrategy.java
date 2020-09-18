package com.lx.rich.strategy;

import java.util.List;
import java.util.Map;

import com.huobi.client.model.Candlestick;
import com.lx.rich.model.Bi;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.model.ZhongShu;
import com.lx.rich.service.ChanService;
import com.lx.rich.service.HistoryDataService;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 18 September 2020
 */
public class DefaultTradeStrategy extends AbstractTradeStrategy implements TradeStrategy {




	@Override
	public boolean shouldBuy() {

		int level = 1;

		Map<Integer, List<ZhongShu>> zhongshuMap = findZhonshuMap(level);

		List<ZhongShu> zhongShus = zhongshuMap.get(level);
		if (zhongShus == null) {
			return false;
		}



		return false;
	}

	@Override
	public boolean shouldSell() {
		return false;
	}
}
