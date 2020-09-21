package com.lx.rich.service;

import com.lx.rich.context.TradeContext;
import com.lx.rich.model.TradeState;

public class TradeService {

    private HistoryDataService historyDataService = HistoryDataService.getInstance();

    public void buy(String code) {
        System.out.println(TradeContext.getTradeType().getText() + " 买入：" + code + ", 价格为：" + historyDataService.getCurrentPrice());
        TradeContext.setTradeState(TradeState.BUY);
    }


    public void sell(String code) {
        System.out.println(TradeContext.getTradeType().getText() + "卖出：" + code + "，价格为：" + historyDataService.getCurrentPrice());
        TradeContext.setTradeState(TradeState.INIT);
    }
}
