package com.lx.rich;

import com.huobi.client.model.Candlestick;
import com.lx.rich.service.HistoryDataService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        HistoryDataService historyDataService = new HistoryDataService();

        List<Candlestick> candlesticks = historyDataService.loadDataFromFile();

        System.out.println(candlesticks.size());


    }


}
