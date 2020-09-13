package com.lx.rich.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.Bi;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.model.ZhongShu;
import com.lx.rich.service.ChanService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZhongshuTest {

    private ChanService chanService = new ChanService();

    @Test
    public void test() {
        ArrayList<Candlestick> candlesticks = Lists.newArrayList(new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
                new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
                new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("5"))


        );

        List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

        List<Bi> bis = chanService.findBi(candleDetails);

        Map<Integer, List<ZhongShu>> zhongshuMap = chanService.findZhongshu(bis, 1);

        System.out.println(JSON.toJSONString(zhongshuMap.get(1)));


    }


    @Test
    public void test1() {
        ArrayList<Candlestick> candlesticks = Lists.newArrayList(new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
                new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
                new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("14"), new BigDecimal("14"))


        );

        List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

        List<Bi> bis = chanService.findBi(candleDetails);

        Map<Integer, List<ZhongShu>> zhongshuMap = chanService.findZhongshu(bis, 1);

        System.out.println(JSON.toJSONString(zhongshuMap.get(1)));


    }


    @Test
    public void test2() {
        ArrayList<Candlestick> candlesticks = Lists.newArrayList(
                new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
                new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
                new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),

                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),

                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),

                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),

                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("14"), new BigDecimal("14")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),

                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8"))


        );

        List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

        List<Bi> bis = chanService.findBi(candleDetails);

        Map<Integer, List<ZhongShu>> zhongshuMap = chanService.findZhongshu(bis, 1);

        System.out.println(JSON.toJSONString(zhongshuMap.get(1)));


    }

    @Test
    public void test3() {
        ArrayList<Candlestick> candlesticks = Lists.newArrayList(
                new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
                new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
                new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),

                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),

                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),

                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),

                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("14"), new BigDecimal("14")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),

                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),

                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),

                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),

                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),

                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),
                new Candlestick(new BigDecimal("14"), new BigDecimal("14")),
                new Candlestick(new BigDecimal("13"), new BigDecimal("13")),

                new Candlestick(new BigDecimal("12"), new BigDecimal("12")),
                new Candlestick(new BigDecimal("11"), new BigDecimal("11")),
                new Candlestick(new BigDecimal("10"), new BigDecimal("10")),
                new Candlestick(new BigDecimal("9"), new BigDecimal("9")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
                new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
                new Candlestick(new BigDecimal("4"), new BigDecimal("4")),

                new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
                new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
                new Candlestick(new BigDecimal("7"), new BigDecimal("7")),
                new Candlestick(new BigDecimal("8"), new BigDecimal("8"))


        );

        List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

        List<Bi> bis = chanService.findBi(candleDetails);

        Map<Integer, List<ZhongShu>> zhongshuMap = chanService.findZhongshu(bis, 2);

        System.out.println(JSON.toJSONString(zhongshuMap.get(2)));


    }
}
