package com.lx.rich.model;

public enum TradeState {
    INIT(0, "没有做任何操作"),
    BUYING(1, "挂单买入中"),
    BUY(2, "已买入"),
    SELLING(3, "挂单卖中"),

    ;

    private final int code;
    private final String text;

    TradeState(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
