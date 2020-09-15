package com.lx.rich;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.Bi;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.model.ZhongShu;
import com.lx.rich.model.Zoushi;
import com.lx.rich.service.ChanService;
import com.lx.rich.service.HistoryDataService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws IOException {

        long eightHour = 8 * 3600 * 1000l;
        HistoryDataService historyDataService = new HistoryDataService();


        List<Candlestick> candlesticks = historyDataService.loadDataFromFile();

        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2020-09-05 23:15:00");
        candlesticks = candlesticks.stream().filter(x->x.getTimestamp() < dateTime.getMillis()).collect(Collectors.toList());
        List<Candlestick> reverse = Lists.reverse(candlesticks);
        ChanService chanService = new ChanService();

        List<CandleDetail> candleDetails = chanService.removeInclude(reverse);

        List<Bi> bis = chanService.findBi(candleDetails);

        int level = 2;

        Map<Integer, List<ZhongShu>> zhongshuMap = chanService.findZhongshu(bis, level);


        for (int i = 0; i < zhongshuMap.get(level).size(); i++) {
            ZhongShu zhongShu = zhongshuMap.get(level).get(i);
            System.out.println("中枢" + i +  ":");
            int j=0;
            for (Zoushi zoushi : zhongShu.getZoushiList()) {
                System.out.println("走势" + j + ": 从" + new Date(zoushi.getFrom().getTimestamp()) +
                        "到" + new Date(zoushi.getTo().getTimestamp()));
                j++;
//                System.out.println("内部的中枢为：");
//                for (ZhongShu innerZhongshu : zoushi.getZhongShuList()) {
//                    List<Zoushi> innerZoushiList = innerZhongshu.getZoushiList();
//                    int j =1;
//                    for (Zoushi innerZoushi : innerZoushiList) {
//                        System.out.println("走势" + j + ":从" + new Date(innerZoushi.getFrom().getTimestamp()) +
//                                "到" + new Date(innerZoushi.getTo().getTimestamp()) );
//                        j++;
//                    }
//
//                }
            }
            System.out.println();

        }


    }


}
