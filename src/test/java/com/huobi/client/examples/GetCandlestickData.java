package com.huobi.client.examples;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.RequestOptions;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.request.CandlestickRequest;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;

public class GetCandlestickData {
  public static void main(String[] args) {

    String accessKey = "1c4601a8-gr4edfki8l-c02981aa-b7379";
    String secretKey = "333fb4f2-5361381d-0aca802a-8c3da";

    // Synchronization mode
    SyncRequestClient syncRequestClient = SyncRequestClient.create(accessKey, secretKey);

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    List<Candlestick> candlestickList = syncRequestClient.getCandlestick(new CandlestickRequest(
        "btcusdt", CandlestickInterval.DAY1, DateTime.parse("2020-06-01 00:00:00", dateTimeFormatter).getMillis(),
            DateTime.parse("2020-09-06 00:00:00", dateTimeFormatter).getMillis(), null));
    System.out.println("---- 1 min candlestick for btcusdt ----, size: " + candlestickList.size());

    for (Candlestick candlestick : candlestickList) {
      System.out.println();
      System.out.println("Timestamp: " + new Date(candlestick.getTimestamp()));
      System.out.println("High: " + candlestick.getHigh());
      System.out.println("Low: " + candlestick.getLow());
      System.out.println("Open: " + candlestick.getOpen());
      System.out.println("Close: " + candlestick.getClose());
      System.out.println("Volume: " + candlestick.getVolume());
    }
    System.out.println();
    // Asynchronization mode
//    AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
//    asyncRequestClient.getLatestCandlestick("btcusdt", CandlestickInterval.MIN1, 1, (candlestickResult) -> {
//      if (candlestickResult.succeeded()) {
//        System.out.println("---- 1 min candlestick for btcusdt ----");
//        for (Candlestick candlestick : candlestickResult.getData()) {
//          System.out.println();
//          System.out.println("Timestamp: " + candlestick.getTimestamp());
//          System.out.println("High: " + candlestick.getHigh());
//          System.out.println("Low: " + candlestick.getLow());
//          System.out.println("Open: " + candlestick.getOpen());
//          System.out.println("Close: " + candlestick.getClose());
//          System.out.println("Volume: " + candlestick.getVolume());
//        }
//      }
//    });
  }
}
