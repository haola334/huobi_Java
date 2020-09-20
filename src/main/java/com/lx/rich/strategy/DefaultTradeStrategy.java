package com.lx.rich.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.huobi.client.model.Candlestick;
import com.lx.rich.context.TradeContext;
import com.lx.rich.model.*;
import com.lx.rich.service.ChanService;
import com.lx.rich.service.HistoryDataService;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 18 September 2020
 */
public class DefaultTradeStrategy extends AbstractTradeStrategy implements TradeStrategy {

	private final int level = 1;

	private BigDecimal zd = null;
	private BigDecimal zg = null;

	@Override
	public boolean shouldBuy() {
		Map<Integer, List<ZhongShu>> zhongshuMap = findZhonshuMap(level);

		List<ZhongShu> zhongShus = zhongshuMap.get(level);
		if (zhongShus == null || zhongShus.isEmpty()) {
			return false;
		}

		List<Integer> beichiLevels = chanService.getBeichiLevel(zhongshuMap, level, BeichiType.QUSHI, 0.5);


		if (!beichiLevels.contains(level)) {
			return false;
		}

		List<ZhongShu> targetZhongshu = zhongShus;

		if (beichiLevels.size() > 1) {
			targetZhongshu = zhongshuMap.get(beichiLevels.get(1));
		}


		ZhongShu lastZhongshu = targetZhongshu.get(targetZhongshu.size() - 1);

		Zoushi zoushi = getLastZoushi(lastZhongshu);

		BigDecimal currentPrice = historyDataService.getCurrentPrice();

		if (zoushi.isUp()) {
			if (currentPrice.compareTo(lastZhongshu.getZg()) <= 0) {
				return false;
			}

			TradeContext.setTradeType(TradeType.KONG);

		} else {
			if (currentPrice.compareTo(lastZhongshu.getZd()) >= 0) {
				return false;
			}
			TradeContext.setTradeType(TradeType.DUO);
		}

		zd = lastZhongshu.getZd();
		zg = lastZhongshu.getZg();

		return true;
	}

	@Override
	public boolean shouldSell() {

		if (level == 1) {

			BigDecimal currentPrice = historyDataService.getCurrentPrice();
			if (TradeContext.getTradeType() == TradeType.KONG) {
				if (currentPrice.compareTo(zg) <= 0) {
					return true;
				}
			} else if (TradeContext.getTradeType() == TradeType.DUO){
				if (currentPrice.compareTo(zd) >= 0) {
					return true;
				}
			}

		}

		return false;
	}
}
