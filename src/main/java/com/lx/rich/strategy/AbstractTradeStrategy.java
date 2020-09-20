package com.lx.rich.strategy;

import java.util.List;
import java.util.Map;

import com.huobi.client.model.Candlestick;
import com.lx.rich.model.Bi;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.model.ZhongShu;
import com.lx.rich.model.Zoushi;
import com.lx.rich.service.ChanService;
import com.lx.rich.service.HistoryDataService;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 18 September 2020
 */
public abstract class AbstractTradeStrategy {

	protected HistoryDataService historyDataService = HistoryDataService.getInstance();

	protected ChanService chanService = new ChanService();


	protected Map<Integer, List<ZhongShu>> findZhonshuMap(int level) {
		List<Candlestick> candlesticks = historyDataService.getCandlesticks();

		List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

		List<Bi> biList = chanService.findBi(candleDetails);

		return chanService.findZhongshu(biList, level);


	}

	protected Zoushi getLastZoushi(ZhongShu zhongShu) {
		return zhongShu.getZoushiList().get(zhongShu.getZoushiList().size() - 1);
	}


}
