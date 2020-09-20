package com.lx.rich.context;

import com.lx.rich.model.TradeType;

public class TradeContext {

    private static TradeType tradeType;

    public static void setTradeType(TradeType tradeType) {
        TradeContext.tradeType = tradeType;
    }

    public static TradeType getTradeType() {
        return tradeType;
    }

    public void clear() {
        tradeType = null;
    }
}
