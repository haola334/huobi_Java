package com.lx.rich.model;

import com.huobi.client.model.Candlestick;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 07 September 2020
 */
public class CandleDetail extends Candlestick {

	private Fenxing fenxing;

	private CandleDetail fenxingLeft; //分型左边的k
	private CandleDetail fenxingRight; //分型左边的k

	public Fenxing getFenxing() {
		return fenxing;
	}

	public void setFenxing(Fenxing fenxing) {
		this.fenxing = fenxing;
	}

	public CandleDetail getFenxingLeft() {
		return fenxingLeft;
	}

	public void setFenxingLeft(CandleDetail fenxingLeft) {
		this.fenxingLeft = fenxingLeft;
	}

	public CandleDetail getFenxingRight() {
		return fenxingRight;
	}

	public void setFenxingRight(CandleDetail fenxingRight) {
		this.fenxingRight = fenxingRight;
	}
}
