package com.lx.rich.service;

import java.util.List;
import java.util.Stack;

import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.Bi;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.model.Fenxing;
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

		Stack<CandleDetail> fenxingStack = new Stack<>();

		List<Bi> biList = Lists.newArrayList();
		for (int i = 0; i < sources.size(); i++) {
			if (i < 2) {
				continue;
			}

			CandleDetail candleDetail1 = sources.get(i);
			CandleDetail candleDetail2 = sources.get(i - 1);
			CandleDetail candleDetail3 = sources.get(i - 2);
			if (isTop(candleDetail1, candleDetail2, candleDetail3)) {
				candleDetail2.setFenxing(Fenxing.TOP);
				candleDetail2.setFenxingLeft(candleDetail1);
				candleDetail2.setFenxingRight(candleDetail3);
			}
			else if (isBottom(candleDetail1, candleDetail2, candleDetail3)) {
				candleDetail2.setFenxing(Fenxing.BOTTOM);
				candleDetail2.setFenxingLeft(candleDetail1);
				candleDetail2.setFenxingRight(candleDetail3);
			}


			if (candleDetail2.getFenxing() != null) {
				CandleDetail currentFenxing = candleDetail2;


				Bi lastBi = biList.isEmpty() ? null : biList.get(biList.size() - 1);

				if (lastBi == null) {

					if (fenxingStack.size() < 3) { //少于3个元素都不能构成笔，直接塞进去，后面再判断
						fenxingStack.push(currentFenxing);
						continue;
					}

					Fenxing targetFenxing = currentFenxing.getFenxing() == Fenxing.TOP ? Fenxing.BOTTOM : Fenxing.TOP;
					//几种情况：
					//1、最标准的，分型1 + n个k + 分型2
					//2、分型1 + n个分型1 + 分型2，这个等价于 分型1+n个k + 分型2 + n个k + 分型1
					//这几种情况都有一个原则，要确定笔的方向，需要找到不包含重复k的最近2个分型。

					boolean findBi = false;
					for (int j = fenxingStack.size() - 2; j >= 0; j--) {
						CandleDetail element = fenxingStack.elementAt(j);

						if (element.getFenxing() == targetFenxing) {
							if (currentFenxing.getFenxing() == Fenxing.TOP) {
								if (element.getLow().compareTo(candleDetail1.getLow()) < 0
										&& element.getLow().compareTo(candleDetail3.getLow()) < 0) {
									Bi bi = new Bi();
									bi.setUp(true);
									bi.setFrom(element);
									bi.setTo(currentFenxing);
									biList.add(bi);
									fenxingStack.clear();
									findBi = true;
									break;
								}
							}
						}
					}

					if (findBi) {
						continue;
					}

					//如果从当前分型往前找，没构成一笔，那么从倒数第一个与之分型相反的分型开始找

					for (int j = fenxingStack.size() - 2; j >= 0; j--) {
						CandleDetail element = fenxingStack.elementAt(j);
						if (element.getFenxing() == )
					}

				}
				else {
					if (currentFenxing.getFenxing() == Fenxing.TOP) {
						//如果是顶分型且最后一笔的方向向上，则判断该顶分型的最高是否高于笔的最高，如果高于，则替换顶
						if (lastBi.isUp()) {
							if (currentFenxing.getHigh().compareTo(lastBi.getTo().getHigh()) >= 0) {
								lastBi.setTo(currentFenxing);
							}
						}

						//如果是向下的一笔，则什么都不用干
					}
					else if (currentFenxing.getFenxing() == Fenxing.BOTTOM) {
						if (!lastBi.isUp()) {
							if (currentFenxing.getLow().compareTo(lastBi.getTo().getLow()) <= 0) {
								lastBi.setTo(currentFenxing);
							}
						}
					}
				}
			}
			else {
				fenxingStack.push(candleDetail1);
			}

		}


		return null;

	}

	private Bi findBi(Stack<CandleDetail> fenxingStack, CandleDetail currentFenxing, CandleDetail fenxingLeft, CandleDetail fenxingRight) {

		Fenxing targetFenxing = currentFenxing.getFenxing() == Fenxing.TOP ? Fenxing.BOTTOM : Fenxing.TOP;

		for (int j = fenxingStack.size() - 2; j >= 0; j--) {
			CandleDetail element = fenxingStack.elementAt(j);

			if (element.getFenxing() == targetFenxing) {
				if (currentFenxing.getFenxing() == Fenxing.TOP) {
					if (element.getLow().compareTo(fenxingLeft.getLow()) < 0
							&& element.getLow().compareTo(fenxingRight.getLow()) < 0) {
						Bi bi = new Bi();
						bi.setUp(true);
						bi.setFrom(element);
						bi.setTo(currentFenxing);
						return bi;
					}
				} else {
					if (element.getHigh().compareTo(fenxingLeft.getHigh()) > 0
							&& element.getHigh().compareTo(fenxingLeft.getHigh()) > 0) {
						Bi bi = new Bi();
						bi.setUp(true);
						bi.setFrom(element);
						bi.setTo(currentFenxing);
						return bi;
					}
				}
			}
		}

		return null;
	}

	private boolean isBottom(CandleDetail candleDetail1, CandleDetail candleDetail2, CandleDetail candleDetail3) {
		return candleDetail2.getHigh().compareTo(candleDetail1.getHigh()) < 0
				&& candleDetail2.getHigh().compareTo(candleDetail3.getHigh()) < 0;
	}

	private boolean isTop(CandleDetail candleDetail1, CandleDetail candleDetail2, CandleDetail candleDetail3) {

		return candleDetail2.getHigh().compareTo(candleDetail1.getHigh()) > 0
				&& candleDetail2.getHigh().compareTo(candleDetail3.getHigh()) > 0;

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
