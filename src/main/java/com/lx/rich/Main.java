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

public class Main {


    public static void main(String[] args) throws IOException {

        long eightHour = 8 * 3600 * 1000l;
        HistoryDataService historyDataService = new HistoryDataService();


        List<Candlestick> candlesticks = historyDataService.loadDataFromFile();

        List<Candlestick> reverse = Lists.reverse(candlesticks);
        ChanService chanService = new ChanService();

        List<CandleDetail> candleDetails = chanService.removeInclude(reverse);

        List<Bi> bis = chanService.findBi(candleDetails);

        Map<Integer, List<ZhongShu>> zhongshuMap = chanService.findZhongshu(bis, 2);


        for (int i = 0; i < zhongshuMap.get(2).size(); i++) {
            ZhongShu zhongShu = zhongshuMap.get(2).get(i);
            System.out.println("中枢" + i +  ":");
            for (Zoushi zoushi : zhongShu.getZoushiList()) {
                System.out.println("走势" + i + ": 从" + new Date(zoushi.getFrom().getTimestamp()) +
                        "到" + new Date(zoushi.getTo().getTimestamp()));

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
