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
		//注意，这个sources需要按时间从小到大排序，不然会有问题
		for (int i = 0; i < sources.size(); i++) {
			if (i < 2) {
				result.add(CandleUtils.toCandleDetail(sources.get(i)));
				continue;
			}

			Candlestick last1 = result.get(result.size() - 1);
			Candlestick last2 = result.get(result.size() - 2);

			boolean up = false;
			boolean down = false;

			if (last1.getHigh().compareTo(last2.getHigh()) > 0) {
				up = true;
			}
			else if (last1.getLow().compareTo(last2.getLow()) < 0) {
				down = true;
			}
			else {
				up = true;
			}

			Candlestick current = sources.get(i);

			if (isInclude(last1, current)) {
				result.set(result.size() - 1, CandleUtils.toCandleDetail(mergeCandle(last1, current, up)));
			}
			else {
				result.add(CandleUtils.toCandleDetail(current));
			}

		}

		//因为头两个k并没有能区分出方向，所以无法merge，
		// 所以如果从第一根到第五根都是包含关系，那么最终的结果是2根


		return result;
	}

	public List<Bi> findBi(List<CandleDetail> sources) {

		Stack<CandleDetail> fenxingStack = new Stack<>();

		List<Bi> biList = Lists.newArrayList();
		//注意，这个sources需要按时间从小到大排序，不然会有问题
		for (int i = 0; i < sources.size(); i++) {
			if (i < 2) {
				continue;
			}

			CandleDetail candleDetail1 = sources.get(i);
			CandleDetail candleDetail2 = sources.get(i - 1);
			CandleDetail candleDetail3 = sources.get(i - 2);
			if (isTop(candleDetail3, candleDetail2, candleDetail1)) {
				candleDetail2.setFenxing(Fenxing.TOP);
				candleDetail2.setFenxingLeft(candleDetail3);
				candleDetail2.setFenxingRight(candleDetail1);
				if (i + 1 < sources.size()) {
					candleDetail2.setFenxingRightAfter(sources.get(i + 1));
				}

				if (i - 3 >= 0) {
					candleDetail2.setFenxingLeftBefore(sources.get(i - 3));
				}
			}
			else if (isBottom(candleDetail3, candleDetail2, candleDetail1)) {
				candleDetail2.setFenxing(Fenxing.BOTTOM);
				candleDetail2.setFenxingLeft(candleDetail3);
				candleDetail2.setFenxingRight(candleDetail1);
				if (i + 1 < sources.size()) {
					candleDetail2.setFenxingRightAfter(sources.get(i + 1));
				}

				if (i - 3 >= 0) {
					candleDetail2.setFenxingLeftBefore(sources.get(i - 3));
				}
			}


			if (candleDetail2.getFenxing() != null) {
				CandleDetail currentFenxing = candleDetail2;


				Bi lastBi = biList.isEmpty() ? null : biList.get(biList.size() - 1);

				if (lastBi == null || !fenxingStack.isEmpty()) {

					if (fenxingStack.size() < 3) { //少于3个元素都不能构成笔，直接塞进去，后面再判断
						fenxingStack.push(currentFenxing);
						continue;
					}

					Fenxing targetFenxing = currentFenxing.getFenxing() == Fenxing.TOP ? Fenxing.BOTTOM : Fenxing.TOP;
					//几种情况：
					//1、最标准的，分型1 + n个k + 分型2
					//2、分型1 + n个分型1 + 分型2，这个等价于 分型1+n个k + 分型2 + n个k + 分型1
					//这几种情况都有一个原则，要确定笔的方向，需要找到不包含重复k的最近2个分型。

					Bi bi = findBi(fenxingStack, currentFenxing, fenxingStack.size());

					if (bi != null) {
						biList.add(bi);
						fenxingStack.clear();
						continue;
					}

					//如果从当前分型往前找，没构成一笔，那么从倒数第一个与之分型相反的分型开始找

					for (int j = fenxingStack.size() - 2; j >= 0; j--) {
						CandleDetail element = fenxingStack.elementAt(j);
						if (element.getFenxing() == targetFenxing) {
							bi = findBi(fenxingStack, element, j);

							if (bi != null) {
								biList.add(bi);
								fenxingStack.clear();
							}

							break;
						}
					}

					if (bi == null) {
						fenxingStack.push(currentFenxing);
					}

				}
				else {
					if (currentFenxing.getFenxing() == Fenxing.TOP) {
						//如果是顶分型且最后一笔的方向向上，则判断该顶分型的最高是否高于笔的最高，如果高于，则替换顶
						if (lastBi.isUp()) {
							if (currentFenxing.getHigh().compareTo(lastBi.getTo().getHigh()) >= 0) {
								lastBi.setTo(currentFenxing);
							}
						} else {

							//如果是向下的一笔，则判断是否构成一笔
							Bi newBi = buildBi(lastBi.getTo(), currentFenxing);
							if (newBi != null) {
								biList.add(newBi);
							}
						}
					}
					else if (currentFenxing.getFenxing() == Fenxing.BOTTOM) {
						if (!lastBi.isUp()) {
							if (currentFenxing.getLow().compareTo(lastBi.getTo().getLow()) <= 0) {
								lastBi.setTo(currentFenxing);
							}
						} else {

							Bi newBi = buildBi(lastBi.getTo(), currentFenxing);
							if (newBi != null) {
								biList.add(newBi);
							}
						}
					}
				}
			}
			else {
				if (!fenxingStack.isEmpty()) {
					fenxingStack.push(candleDetail1);
				}
			}

		}


		return biList;

	}

	private Bi buildBi(CandleDetail from, CandleDetail to) {

		if (canBuildBi(from, to)) {
			Bi bi = new Bi();
			bi.setFrom(from);
			bi.setTo(to);
			bi.setUp(from.getFenxing() == Fenxing.BOTTOM);
			return bi;
		}

		return null;
	}

	private boolean canBuildBi(CandleDetail from, CandleDetail to) {


		if (from == to || from.getFenxingLeft() == to.getFenxingLeft()
				|| from.getFenxingLeft() == to.getFenxingLeftBefore()
				|| from.getFenxingLeft() == to.getFenxingRight()
				|| from.getFenxingLeft() == to.getFenxingRightAfter()
				|| from.getFenxingRight() == to.getFenxingLeft()
				|| from.getFenxingRight() == to.getFenxingLeftBefore()
				|| from.getFenxingRight() == to.getFenxingRight()
				|| from.getFenxingRight() == to.getFenxingRightAfter()
				|| (from.getFenxingLeftBefore() != null && (from.getFenxingLeftBefore() == to.getFenxingLeft()))
				|| (from.getFenxingLeftBefore() != null && (from.getFenxingLeftBefore() == to.getFenxingLeftBefore()))
				|| (from.getFenxingLeftBefore() != null && (from.getFenxingLeftBefore() == to.getFenxingRight()))
				|| (from.getFenxingLeftBefore() != null && (from.getFenxingLeftBefore() == to.getFenxingRightAfter()))
				|| (from.getFenxingRightAfter() != null && (from.getFenxingRightAfter() == to.getFenxingLeft()))
				|| (from.getFenxingRightAfter() != null && (from.getFenxingRightAfter() == to.getFenxingRight()))
				|| (from.getFenxingRightAfter() != null && (from.getFenxingRightAfter() == to.getFenxingRightAfter()))) {
			return false;
		}

		if (from.getFenxing() == Fenxing.TOP) {
			if (from.getHigh().compareTo(to.getLow()) <= 0) {
				return false;
			}
		} else {
			if (from.getLow().compareTo(to.getHigh()) >= 0) {
				return false;
			}
		}

		return true;
	}

	private Bi findBi(Stack<CandleDetail> fenxingStack, CandleDetail currentFenxing, int curIndex) {

		CandleDetail fenxingLeft = currentFenxing.getFenxingLeft();
		CandleDetail fenxingRight = currentFenxing.getFenxingRight();

		Fenxing targetFenxing = currentFenxing.getFenxing() == Fenxing.TOP ? Fenxing.BOTTOM : Fenxing.TOP;

		for (int j = curIndex - 2; j >= 0; j--) {
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
				}
				else {
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
