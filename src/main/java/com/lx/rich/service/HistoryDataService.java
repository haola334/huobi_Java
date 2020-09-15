package com.lx.rich.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.huobi.client.SubscriptionErrorHandler;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.event.CandlestickEvent;
import com.huobi.client.model.request.CandlestickRequest;
import com.lx.rich.supplier.RequestClientSupplier;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;

public class HistoryDataService {
    private final int ONE_MIN = 60; //1分钟
    private final int BATCH_SIZE = 300 * ONE_MIN; //300分钟

    private final long EIGHT_HOUR = 8 * 3600 * 1000l;
    /**
     * 都是获取1min级别的数据，因为我所有的级别都是从1min往上递归得来的
     */
    public void loadHistoryData(BatchProcessor batchProcessor) {

        List<Candlestick> candlestickList = RequestClientSupplier.syncRequestClient.getCandlestick(new CandlestickRequest(
                "btcusdt", CandlestickInterval.MIN1, null,
                null, 2000));
        System.out.println("当前加载的时间为：" + new DateTime().toString("yyyy-MM-dd HH:mm"));

        if (batchProcessor != null) {
            candlestickList.forEach(x->x.setTimestamp(x.getTimestamp() + EIGHT_HOUR));
            batchProcessor.process(candlestickList);
        }
    }

    public void loadAndSave() throws IOException {

        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath();//标准的路径 ;
        File file = new File(courseFile + "\\src\\main\\resources\\data.txt");

        loadHistoryData(new BatchProcessor() {
            @Override
            public void process(List<Candlestick> candlesticks) {



                String json = JSON.toJSONString(candlesticks);
                try {
                    Files.write(json, file, Charset.defaultCharset());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public List<Candlestick> loadDataFromFile() throws IOException {
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath();//标准的路径 ;
//        File file = new File(courseFile + "\\src\\main\\resources\\data.txt");
        File file = new File("/Users/liangxiao/IdeaProjects/huobi_Java/src/main/resources/data.txt");
        List<String> lines = Files.readLines(file, Charset.defaultCharset());

        String line = lines.get(0);

        List<Candlestick> candlesticks = JSON.parseArray(line, Candlestick.class);
        candlesticks.forEach(x -> x.setTimestamp(x.getTimestamp() + EIGHT_HOUR));
        return candlesticks;

    }

    public static interface BatchProcessor {
        void process(List<Candlestick> candlesticks);
    }

}
