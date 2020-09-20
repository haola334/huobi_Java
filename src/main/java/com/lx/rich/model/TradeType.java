package com.lx.rich.model;

public enum  TradeType {
    DUO(0, "做多"),
    KONG(1, "做空"),
    ;

    private final int code;
    private final String text;

    TradeType(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
