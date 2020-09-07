package com.lx.rich.model;

/**
 * 笔
 *
 * @author xiao.liang
 * @since 07 September 2020
 */
public class Bi {

	private CandleDetail from; //笔的起始点
	private CandleDetail to; //笔的终点
	private boolean up; //是否是向上的笔

	public CandleDetail getFrom() {
		return from;
	}

	public void setFrom(CandleDetail from) {
		this.from = from;
	}

	public CandleDetail getTo() {
		return to;
	}

	public void setTo(CandleDetail to) {
		this.to = to;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}
}
