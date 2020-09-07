package com.lx.rich.model;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 07 September 2020
 */
public enum Fenxing {
	TOP(0, "顶"),
	BOTTOM(1, "底"),
	;

	private final int code;
	private final String text;


	Fenxing(int code, String text) {
		this.code = code;
		this.text = text;
	}
}
