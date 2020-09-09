package com.lx.rich.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.huobi.client.model.Candlestick;
import com.lx.rich.model.Bi;
import com.lx.rich.model.CandleDetail;
import com.lx.rich.service.ChanService;
import org.junit.Test;

/**
 * TODO completion javadoc.
 *
 * @author xiao.liang
 * @since 09 September 2020
 */
public class BiTest {

	private ChanService chanService = new ChanService();

	@Test
	public void test1() {
		ArrayList<Candlestick> candlesticks = Lists.newArrayList(new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
				new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
				new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
				new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
				new Candlestick(new BigDecimal("4"), new BigDecimal("4"))
		);

		List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

		List<Bi> bis = chanService.findBi(candleDetails);

		System.out.println(JSON.toJSONString(bis));

	}

	@Test
	public void test2() {
		ArrayList<Candlestick> candlesticks = Lists.newArrayList(new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
				new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
				new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
				new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
				new Candlestick(new BigDecimal("6"), new BigDecimal("6")),
				new Candlestick(new BigDecimal("4"), new BigDecimal("4"))
		);

		List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

		List<Bi> bis = chanService.findBi(candleDetails);

		System.out.println(JSON.toJSONString(bis));

	}

	@Test
	public void tes3() {
		ArrayList<Candlestick> candlesticks = Lists.newArrayList(new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
				new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
				new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
				new Candlestick(new BigDecimal("5"), new BigDecimal("5")),
				new Candlestick(new BigDecimal("4"), new BigDecimal("4")),
				new Candlestick(new BigDecimal("3"), new BigDecimal("3")),
				new Candlestick(new BigDecimal("2"), new BigDecimal("2")),
				new Candlestick(new BigDecimal("1"), new BigDecimal("1")),
				new Candlestick(new BigDecimal("2"), new BigDecimal("2"))
		);

		List<CandleDetail> candleDetails = chanService.removeInclude(candlesticks);

		List<Bi> bis = chanService.findBi(candleDetails);

		System.out.println(JSON.toJSONString(bis));

	}


}
