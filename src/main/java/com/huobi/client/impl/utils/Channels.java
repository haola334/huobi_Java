package com.huobi.client.impl.utils;

import com.alibaba.fastjson.JSONObject;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.CandlestickInterval;

public abstract class Channels {

  public static String klineChannel(String symbol, CandlestickInterval interval, Integer from, Integer to) {
    JSONObject json = new JSONObject();
    String sub = "market." + symbol + ".kline." + interval.toString();
    if (from != null && to != null) {
      json.put("req", sub);
    } else {
      json.put("sub", sub);
    }
    json.put("id", TimeService.getCurrentTimeStamp() + "");
    json.put("from", from);
    json.put("to", to);
    return json.toJSONString();
  }

  public static String priceDepthChannel(String symbol) {
    JSONObject json = new JSONObject();
    json.put("sub", "market." + symbol + ".depth.step0");
    json.put("id", TimeService.getCurrentTimeStamp() + "");
    return json.toJSONString();
  }

  public static String tradeChannel(String symbol) {
    JSONObject json = new JSONObject();
    json.put("sub", "market." + symbol + ".trade.detail");
    json.put("id", TimeService.getCurrentTimeStamp() + "");
    return json.toJSONString();
  }

  public static String accountChannel(BalanceMode mode) {
    JSONObject json = new JSONObject();
    json.put("op", "sub");
    json.put("cid", TimeService.getCurrentTimeStamp() + "");
    json.put("topic", "accounts");
    if (mode != null) {
      json.put("model", mode.getCode());
    }
    return json.toJSONString();
  }

  public static String ordersChannel(String symbol) {
    JSONObject json = new JSONObject();
    json.put("op", "sub");
    json.put("cid", TimeService.getCurrentTimeStamp() + "");
    json.put("topic", "orders." + symbol);
    return json.toJSONString();
  }

  public static String tradeStatisticsChannel(String symbol) {
    JSONObject json = new JSONObject();
    json.put("sub", "market." + symbol + ".detail");
    json.put("id", TimeService.getCurrentTimeStamp() + "");
    return json.toJSONString();
  }
}
