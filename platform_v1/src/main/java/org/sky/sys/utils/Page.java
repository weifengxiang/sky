package org.sky.sys.utils;

/**
 * @author weifengxiang911@163.com
 * @date 2011-12-1 上午11:36:12
 */
public class Page {
	// 分页查询开始记录位置
	private int begin;
	// 分页查看下结束位置
	private int end;
	// 每页显示记录数
	private int rows;
	// 查询结果总记录数
	private int totalCount;
	// 当前页码
	private int pageIndex;
	// 总共页数
	private int totalPage;

	public Page() {
	}

	/**
	 * 构造函数
	 * @param begin(开始位置)
	 * @param rows(每页记录)
	 */
	public Page(int begin, int rows) {
		this.begin = begin;
		this.rows = rows;
		this.end = this.begin + this.rows;
		this.pageIndex = (int) Math.floor((this.begin * 1.0d) / this.rows) + 1;
	}

	/**
	 * 构造函数
	 * @param begin(开始位置)
	 * @param rows(每页记录)
	 * @param totalCount(总记录数)
	 */
	public Page(int begin, int rows, int totalCount) {
		this(begin, rows);
		this.totalCount = totalCount;
	}

	/**
	 * @return the begin
	 */
	public int getBegin() {
		return begin;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @param begin
	 *            the begin to set
	 */
	public void setBegin(int begin) {
		this.begin = begin;
		if (this.totalCount != 0) {
			this.pageIndex = (int) Math.floor((this.begin * 1.0d) / this.totalCount) + 1;
		}
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


}