package com.lx.rich.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 中枢。只把包含重复区间的三个走势作为中枢，多了也只算最初出现的3个走势
 *
 * @author xiao.liang
 * @since 10 September 2020
 */
public class ZhongShu {

	//中枢内的笔，按时间从早到晚排
	private List<Zoushi> zoushiList;
	//中枢低点
	private BigDecimal zd;
	//中枢高点
	private BigDecimal zg;

	//中枢级别，这个级别不一定是按书中讲的1min，5min，30min的来，
	// 而是应该按递归的层级来，这样才是正宗的递归，很多人都搞不清楚
	private int level;

	public List<Zoushi> getZoushiList() {
		return zoushiList;
	}

	public void setZoushiList(List<Zoushi> zoushiList) {
		this.zoushiList = zoushiList;
	}

	public BigDecimal getZd() {
		return zd;
	}

	public void setZd(BigDecimal zd) {
		this.zd = zd;
	}

	public BigDecimal getZg() {
		return zg;
	}

	public void setZg(BigDecimal zg) {
		this.zg = zg;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
