package com.lx.rich;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.engine.TradeEngine;
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
        TradeEngine tradeEngine = new TradeEngine();

        tradeEngine.start();


    }


}
