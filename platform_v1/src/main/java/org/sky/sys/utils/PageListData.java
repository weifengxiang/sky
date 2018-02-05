package org.sky.sys.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分页数据格式
 * @author Administrator
 *
 */
public class PageListData {
	
	private long total;
	private List rows;

	public PageListData() {
		// TODO Auto-generated constructor stub
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
}
