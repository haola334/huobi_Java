package com.lx.rich.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.Bi;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.utils.CandleUtils;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 07 September 2020
 */
public class ChanService {

	/**
	 * 处理包含关系
	 * @param sources
	 * @return
	 */
	public List<CandleDetail> removeInclude(List<Candlestick> sources) {
		List<CandleDetail> result = Lists.newArrayList();
		//注意，这个sources的倒序排的，最新的放在最前面
		for (int i = 0; i < sources.size(); i++) {
			if (i < 2) {
				result.add(CandleUtils.toCandleDetail(sources.get(i)));
				continue;
			}

			Candlestick last1 = result.get(result.size() - 1);
			Candlestick last2 = result.get(result.size() - 2);

			boolean up = false;
			boolean down = false;

			if (last2.getHigh().compareTo(last1.getHigh()) > 0) {
				up = true;
			}
			else if (last2.getLow().compareTo(last1.getLow()) < 0) {
				down = true;
			}
			else {
				up = true;
			}

			Candlestick current = sources.get(i);

			if (isInclude(current, last1)) {
				result.set(i - 1, CandleUtils.toCandleDetail(mergeCandle(current, last1, up)));
			}
			else {
				result.add(CandleUtils.toCandleDetail(current));
			}

		}

		return result;
	}

	public List<Bi> findBi(List<CandleDetail> sources) {

		return null;


	}


	private Candlestick mergeCandle(Candlestick left, Candlestick right, boolean up) {
		if (left.getHigh().compareTo(right.getHigh()) >= 0 &&
				left.getLow().compareTo(right.getLow()) <= 0) { //左包含
			if (up) {
				Candlestick candlestick = new Candlestick();
				candlestick.setAmount(left.getAmount());
				candlestick.setClose(left.getClose());
				candlestick.setHigh(left.getHigh());
				candlestick.setLow(right.getLow());
				candlestick.setOpen(left.getOpen());
				candlestick.setCount(left.getCount());
				candlestick.setTimestamp(left.getTimestamp());
				candlestick.setVolume(left.getVolume());
				return candlestick;
			}
			else {
				Candlestick candlestick = new Candlestick();
				candlestick.setAmount(left.getAmount());
				candlestick.setClose(left.getClose());
				candlestick.setHigh(right.getHigh());
				candlestick.setLow(left.getLow());
				candlestick.setOpen(left.getOpen());
				candlestick.setCount(left.getCount());
				candlestick.setTimestamp(left.getTimestamp());
				candlestick.setVolume(left.getVolume());
				return candlestick;
			}
		}

		if (right.getHigh().compareTo(left.getHigh()) >= 0 &&
				right.getLow().compareTo(left.getLow()) <= 0) { //右包含
			if (up) {
				Candlestick candlestick = new Candlestick();
				candlestick.setAmount(right.getAmount());
				candlestick.setClose(right.getClose());
				candlestick.setHigh(right.getHigh());
				candlestick.setLow(left.getLow());
				candlestick.setOpen(right.getOpen());
				candlestick.setCount(right.getCount());
				candlestick.setTimestamp(right.getTimestamp());
				candlestick.setVolume(right.getVolume());
				return candlestick;
			}
			else {
				Candlestick candlestick = new Candlestick();
				candlestick.setAmount(right.getAmount());
				candlestick.setClose(right.getClose());
				candlestick.setHigh(left.getHigh());
				candlestick.setLow(right.getLow());
				candlestick.setOpen(right.getOpen());
				candlestick.setCount(right.getCount());
				candlestick.setTimestamp(right.getTimestamp());
				candlestick.setVolume(right.getVolume());
				return candlestick;
			}
		}

		return null;
	}

	private boolean isInclude(Candlestick left, Candlestick right) {
		if (left.getHigh().compareTo(right.getHigh()) >= 0 &&
				left.getLow().compareTo(right.getLow()) <= 0) { //左包含
			return true;
		}

		if (right.getHigh().compareTo(left.getHigh()) >= 0 &&
				right.getLow().compareTo(left.getLow()) <= 0) {
			return true;
		}

		return false;
	}

}
