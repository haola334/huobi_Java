package com.lx.rich.loader;

import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.service.HistoryDataService;
import com.lx.rich.service.MacdService;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BtcPriceLoader extends AbstracePriceLoader {

    private HistoryDataService historyDataService = HistoryDataService.getInstance();
    private MacdService macdService = MacdService.getInstance();

    private List<Candlestick> all;

    private AtomicInteger index = new AtomicInteger(0);

    public BtcPriceLoader() {
        try {
            all = historyDataService.loadDataFromFile();
            for (int i = 0; i<26; i++) {
                historyDataService.addCandlestick(all.get(index.getAndIncrement()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void load() {

        Candlestick candlestick = all.get(index.getAndIncrement());
        historyDataService.addCandlestick(candlestick);
        int size = historyDataService.getCandlesticks().size();
        macdService.buildCache(historyDataService.getCandlesticks().subList(size - 26, size));
//        System.out.println("当前时间：" + new Date(candlestick.getTimestamp()) + ": 价格为" + candlestick.getClose());

    }
}
