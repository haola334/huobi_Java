package com.lx.rich.context;

import com.lx.rich.model.TradeState;
import com.lx.rich.model.TradeType;

import java.util.concurrent.atomic.AtomicBoolean;

public class TradeContext {

    private volatile static TradeType tradeType;

    private volatile static TradeState tradeState = TradeState.INIT;

    public static void setTradeState(TradeState tradeState) {
        TradeContext.tradeState = tradeState;
    }

    public static TradeState getTradeState() {
        return tradeState;
    }

    public static void setTradeType(TradeType tradeType) {
        TradeContext.tradeType = tradeType;
    }

    public static TradeType getTradeType() {
        return tradeType;
    }

    public void clear() {
        tradeType = null;
        tradeState = null;
    }
}
