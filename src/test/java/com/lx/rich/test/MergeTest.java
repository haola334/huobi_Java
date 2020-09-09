package com.lx.rich.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.service.ChanService;
import com.sun.source.tree.AssertTree;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 09 September 2020
 */
public class MergeTest {

	private ChanService chanService = new ChanService();

	@Test
	public void testNoMerge() {
		//不需要merge的情况
		ArrayList<Candlestick> candlesticks = Lists.newArrayList(new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
				new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
				new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
				new Candlestick(new BigDecimal("5"), new BigDecimal("5"))
		);

		List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

		Assert.that(candleDetails.size() == candlesticks.size(), "不包含的情况下不应该merge");

	}

	@Test
	public void testMerge1() {
		ArrayList<Candlestick> candlesticks = Lists.newArrayList(new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("3")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("4")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("5"))
		);

		List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

		//因为头两个k并没有能区分出方向，所以无法merge，
		// 所以如果从第一根到第五根都是包含关系，那么最终的结果是2根
		System.out.println(JSON.toJSONString(candleDetails));

		Assert.that(candleDetails.size() == 2, "应该是2根！");
	}


	@Test
	public void testMerge2() {
		ArrayList<Candlestick> candlesticks = Lists.newArrayList(new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
				new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
				new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
				new Candlestick(new BigDecimal("3"), new BigDecimal("5"))
		);

		List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

		System.out.println(JSON.toJSONString(candleDetails));
	}


}
