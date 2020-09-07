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

	public Fenxing getFenxing() {
		return fenxing;
	}

	public void setFenxing(Fenxing fenxing) {
		this.fenxing = fenxing;
	}
}
