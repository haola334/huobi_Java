package com.lx.rich.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.Bi;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.model.Fenxing;
import com.lx.rich.model.ZhongShu;
import com.lx.rich.model.Zoushi;
import com.lx.rich.utils.CandleUtils;
import javafx.util.Pair;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 07 September 2020
 */
public class ChanService {

    /**
     * 处理包含关系
     *
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
            } else if (last1.getLow().compareTo(last2.getLow()) < 0) {
                down = true;
            } else {
                up = true;
            }

            Candlestick current = sources.get(i);

            if (isInclude(last1, current)) {
                result.set(result.size() - 1, CandleUtils.toCandleDetail(mergeCandle(last1, current, up)));
            } else {
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
            } else if (isBottom(candleDetail3, candleDetail2, candleDetail1)) {
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

                } else {
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
                    } else if (currentFenxing.getFenxing() == Fenxing.BOTTOM) {
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
            } else {
                if (!fenxingStack.isEmpty()) {
                    fenxingStack.push(candleDetail1);
                }
            }

        }


        return biList;

    }

    private List<ZhongShu> findZhongshu(List<Zoushi> zoushiList) {
        List<ZhongShu> zhongShus = Lists.newArrayList();

        List<Zoushi> latestZoushi = Lists.newArrayList();    //用来存最近几笔


        for (int i = 0; i < zoushiList.size(); i++) {
            Zoushi currZoushi = zoushiList.get(i);

            //从第四笔开始看
            latestZoushi.add(currZoushi);
            if (latestZoushi.size() < 4) {
                continue;
            }

            Pair<BigDecimal, BigDecimal> zhongshuRange = findZhongshuRange(latestZoushi);

            if (zhongshuRange != null) {
                ZhongShu zhongShu = new ZhongShu();

                zhongShu.setZoushiList(latestZoushi.subList(1, latestZoushi.size()));
                zhongShu.setLevel(currZoushi.getLevel());
                zhongShu.setZd(zhongshuRange.getKey());
                zhongShu.setZg(zhongshuRange.getValue());

                zhongShus.add(zhongShu);
                latestZoushi = Lists.newArrayList();
            }

        }

        return zhongShus;

    }

    //找到给定级别及其以下级别的中枢
    public Map<Integer, List<ZhongShu>> findZhongshu(List<Bi> biList, int level) {

        List<ZhongShu> zhongShus = Lists.newArrayList();

        Map<Integer, List<ZhongShu>> zhongshuMap = Maps.newHashMap();
        //第一级别的中枢很简单，就直接找重叠部分即可
        List<Zoushi> zoushiList = biList.stream().map(x -> biToZoushi(x)).collect(Collectors.toList());

        zhongshuMap.put(1, findZhongshu(zoushiList));
        if (level == 1) {
            return zhongshuMap;
        }

        for (int i = 2; i <= level; i++) {
            zhongshuMap.put(i, findFromLowLevelZhongshus(zhongshuMap.get(i - 1)));
        }

        return zhongshuMap;
    }

    private Zoushi biToZoushi(Bi bi) {
        Zoushi zoushi = new Zoushi();
        zoushi.setUp(bi.isUp());
        zoushi.setFrom(bi.getFrom());
        zoushi.setTo(bi.getTo());
        zoushi.setLevel(1);

        return zoushi;
    }

    private List<ZhongShu> findFromLowLevelZhongshus(List<ZhongShu> zhongShus) {

        //先构造走势
        List<Zoushi> zoushis = Lists.newArrayList();

        List<ZhongShu> latestZhongshus = Lists.newArrayList();
        for (int i = 0; i < zhongShus.size(); i++) {
            ZhongShu currZhongshu = zhongShus.get(i);

            if (i < 2) {
                latestZhongshus.add(currZhongshu);
                continue;
            }

            if (isReverseZhongshu(latestZhongshus, currZhongshu)) {
                Zoushi zoushi = new Zoushi();
                zoushi.setZhongShuList(latestZhongshus);
                zoushi.setUp(isZhongshuUp(latestZhongshus));

                ZhongShu firstZhongshu = latestZhongshus.get(0);
                ZhongShu lastZhongshu = latestZhongshus.get(latestZhongshus.size() - 1);

                zoushi.setLevel(firstZhongshu.getLevel() + 1);

                zoushi.setFrom(zoushi.isUp() ? lowCandle(firstZhongshu.getZoushiList().get(firstZhongshu.getZoushiList().size() - 1)) :
                        highCandle(firstZhongshu.getZoushiList().get(firstZhongshu.getZoushiList().size() - 1)));
                zoushi.setTo(zoushi.isUp() ? highCandle(lastZhongshu.getZoushiList().get(lastZhongshu.getZoushiList().size() - 1)) :
                        lowCandle(lastZhongshu.getZoushiList().get(lastZhongshu.getZoushiList().size() - 1)));
                zoushis.add(zoushi);

                latestZhongshus = Lists.newArrayList();
                latestZhongshus.add(zoushi.getZhongShuList().get(zoushi.getZhongShuList().size() - 1));
                latestZhongshus.add(currZhongshu);
            } else {
                latestZhongshus.add(currZhongshu);
            }

        }

        if (!sameList(zoushis.get(zoushis.size() - 1).getZhongShuList(), latestZhongshus)) {
            Zoushi zoushi = new Zoushi();
            zoushi.setZhongShuList(latestZhongshus);
            zoushi.setUp(isZhongshuUp(latestZhongshus));

            ZhongShu firstZhongshu = latestZhongshus.get(0);
            ZhongShu lastZhongshu = latestZhongshus.get(latestZhongshus.size() - 1);

            zoushi.setLevel(firstZhongshu.getLevel() + 1);

            zoushi.setFrom(zoushi.isUp() ? lowCandle(firstZhongshu.getZoushiList().get(firstZhongshu.getZoushiList().size() - 1)) :
                    highCandle(firstZhongshu.getZoushiList().get(firstZhongshu.getZoushiList().size() - 1)));
            zoushi.setTo(zoushi.isUp() ? highCandle(lastZhongshu.getZoushiList().get(lastZhongshu.getZoushiList().size() - 1)) :
                    lowCandle(lastZhongshu.getZoushiList().get(lastZhongshu.getZoushiList().size() - 1)));
            zoushis.add(zoushi);
        }


        return findZhongshu(zoushis);
    }

    private CandleDetail lowCandle(Zoushi zoushi) {
        return lowCandle(zoushi.getFrom(), zoushi.getTo());
    }

    private CandleDetail highCandle(Zoushi zoushi) {
        return highCandle(zoushi.getFrom(), zoushi.getTo());
    }

    private CandleDetail lowCandle(CandleDetail candleDetail1, CandleDetail candleDetail2) {
        if (candleDetail1.getLow().compareTo(candleDetail2.getLow()) < 0) {
            return candleDetail1;
        }

        return candleDetail2;
    }

    private CandleDetail highCandle(CandleDetail candleDetail1, CandleDetail candleDetail2) {
        if (candleDetail1.getHigh().compareTo(candleDetail2.getHigh()) > 0) {
            return candleDetail1;
        }

        return candleDetail2;
    }

    private boolean sameList(List<ZhongShu> list1, List<ZhongShu> list2) {

        if (list1 == list2) {
            return true;
        }

        if (list1 == null || list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean isZhongshuUp(List<ZhongShu> latestZhongshus) {
        ZhongShu lastZhongshu1 = latestZhongshus.get(latestZhongshus.size() - 1);
        ZhongShu lastZhongshu2 = latestZhongshus.get(latestZhongshus.size() - 2);

        boolean up = false;

        if (lastZhongshu1.getZg().compareTo(lastZhongshu2.getZg()) >= 0) {
            up = true;
        }

        return up;
    }

    //是否是方向与之前相反的中枢
    private boolean isReverseZhongshu(List<ZhongShu> latestZhongshus, ZhongShu currZhongshu) {

        ZhongShu lastZhongshu1 = latestZhongshus.get(latestZhongshus.size() - 1);
        ZhongShu lastZhongshu2 = latestZhongshus.get(latestZhongshus.size() - 2);

        boolean up = isZhongshuUp(latestZhongshus);

        if (up) {
            if (currZhongshu.getZg().compareTo(lastZhongshu1.getZg()) < 0) {
                return true;
            }
        } else {
            if (currZhongshu.getZd().compareTo(lastZhongshu1.getZd()) > 0) {
                return true;
            }
        }

        return false;
    }

    private Pair<BigDecimal, BigDecimal> findZhongshuRange(List<Zoushi> zoushiList) {
        //特征笔，即倒数第二笔
        Zoushi tezhengZoushi = zoushiList.get(zoushiList.size() - 2);
        Zoushi firstBi = zoushiList.get(zoushiList.size() - 4);

        if (tezhengZoushi.isUp()) {
            if (tezhengZoushi.getFrom().getLow().compareTo(firstBi.getTo().getHigh()) < 0 &&
                    tezhengZoushi.getTo().getHigh().compareTo(firstBi.getFrom().getLow()) > 0) { //fixme? 这里我不把等于的算在里面，应该会保险一点

                return new Pair<>(tezhengZoushi.getFrom().getLow(), min(firstBi.getTo().getHigh(), tezhengZoushi.getTo().getHigh()));

            }
        } else {
            //倒数第二笔的高点大于倒数第一笔的低点即可
            if (tezhengZoushi.getFrom().getHigh().compareTo(firstBi.getTo().getLow()) > 0
                    && tezhengZoushi.getTo().getLow().compareTo(firstBi.getFrom().getHigh()) < 0) {

                return new Pair<>(max(firstBi.getTo().getLow(), tezhengZoushi.getTo().getLow()), tezhengZoushi.getFrom().getHigh());

            }
        }

        return null;

    }

    private BigDecimal min(BigDecimal num1, BigDecimal num2) {
        if (num1.compareTo(num2) <= 0) {
            return num1;
        }

        return num2;
    }

    private BigDecimal max(BigDecimal num1, BigDecimal num2) {
        if (num1.compareTo(num2) >= 0) {
            return num1;
        }

        return num2;
    }

    private boolean inRange(BigDecimal dot, BigDecimal rangeLow, BigDecimal rangeHigh) {
        return dot.compareTo(rangeLow) > 0 && dot.compareTo(rangeHigh) < 0;
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
            } else {
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
            } else {
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
