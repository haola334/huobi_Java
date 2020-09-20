package com.lx.rich.engine;

import com.lx.rich.context.TradeContext;
import com.lx.rich.loader.BtcPriceLoader;
import com.lx.rich.loader.PriceLoader;
import com.lx.rich.model.TradeState;
import com.lx.rich.service.TradeService;
import com.lx.rich.strategy.DefaultTradeStrategy;
import com.lx.rich.strategy.TradeStrategy;

public class TradeEngine {

    private TradeStrategy tradeStrategy = new DefaultTradeStrategy();

    private TradeService tradeService = new TradeService();

    private PriceLoader priceLoader = new BtcPriceLoader();

    public void start() {

        priceLoader.startLoad();

        String tradeCode = "btcusdt";

        while (true) {
            TradeState tradeState = TradeContext.getTradeState();
            if (tradeState == TradeState.INIT) {
                if (tradeStrategy.shouldBuy()) {
                    tradeService.buy(tradeCode);
                }
            } else if (tradeState == TradeState.BUY) {
                if (tradeStrategy.shouldSell()) {
                    tradeService.sell(tradeCode);
                }
            }
            try {
                Thread.sleep(5L);
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }

}
