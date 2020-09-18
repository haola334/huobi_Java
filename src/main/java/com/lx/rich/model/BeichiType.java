package com.lx.rich.model;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 18 September 2020
 */
public enum BeichiType {
	ALL(0, "所有"),
	PANZHENG(1, "盘整背驰"),
	QUSHI(2, "趋势背驰"),

	;

	private final int code;
	private final String text;

	BeichiType(int code, String text) {
		this.code = code;
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public String getText() {
		return text;
	}
}
