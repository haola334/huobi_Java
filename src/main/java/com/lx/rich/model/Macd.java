package com.lx.rich.model;

import java.math.BigDecimal;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 15 September 2020
 */
public class Macd {

	private BigDecimal dif;
	private BigDecimal dea;
	private BigDecimal macd;

	public BigDecimal getDif() {
		return dif;
	}

	public void setDif(BigDecimal dif) {
		this.dif = dif;
	}

	public BigDecimal getDea() {
		return dea;
	}

	public void setDea(BigDecimal dea) {
		this.dea = dea;
	}

	public BigDecimal getMacd() {
		return macd;
	}

	public void setMacd(BigDecimal macd) {
		this.macd = macd;
	}
}
