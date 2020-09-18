package com.lx.rich.strategy;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 18 September 2020
 */
public interface TradeStrategy {

	boolean shouldBuy();

	boolean shouldSell();

}
