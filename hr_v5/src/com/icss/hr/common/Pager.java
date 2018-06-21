package com.icss.hr.common;

/**
 * 分页工具类
 * @author Administrator
 *
 */
public class Pager {
	
	private int recordCount;//总记录数
	
	private int pageSize = 10;//每页多少条
	
	private int pageCount;//共几页
	
	private int pageNum;//当前页码
	
	private int start;//记录起始位置
	
	private int end;//记录终止位置	
	
	public Pager(int recordCount,int pageNum) {
		this(recordCount,10, pageNum);
	}
	
	public Pager(int recordCount,int pageSize,int pageNum) {
		
		//初始化分页属性
		this.recordCount = recordCount;
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		
		//计算共几页
		this.pageCount = this.recordCount / this.pageSize;
		if (this.recordCount % this.pageSize != 0)
			this.pageCount ++;
		
		//计算当前页码	
		if (this.pageNum < 1)
			this.pageNum = 1;
		
		if (this.pageNum > this.pageCount)
			this.pageNum = this.pageCount;		
		
		//计算记录起始和终止位置
		this.start = (this.pageNum - 1) * this.pageSize;
		this.end = this.pageNum * this.pageSize;
		
		//如果总记录数为0
		if (this.recordCount == 0)
			this.start = 0;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
}