package com.lx.rich.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 走势：最低级别一笔就算一个走势，
 * 从第二级别开始，一个走势在一个方向上至少包含2个中枢，
 * 这两个中枢之间可以有一个次级别走势，也可以没有
 *
 * @author xiao.liang
 * @since 10 September 2020
 */
public class Zoushi extends Bi {

	private List<ZhongShu> zhongShuList;

	private int level;


	public List<ZhongShu> getZhongShuList() {
		return zhongShuList;
	}

	public void setZhongShuList(List<ZhongShu> zhongShuList) {
		this.zhongShuList = zhongShuList;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
