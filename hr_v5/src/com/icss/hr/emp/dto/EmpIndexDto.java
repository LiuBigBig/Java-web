package com.icss.hr.emp.dto;

import java.util.List;

import com.icss.hr.emp.pojo.Emp;

/**
 * 封装全文检索的结果
 * @author Administrator
 *
 */
public class EmpIndexDto {
	
	private int recordCount;
	
	private List<Emp> list;

	public EmpIndexDto() {
		super();
	}

	public EmpIndexDto(int recordCount, List<Emp> list) {
		super();
		this.recordCount = recordCount;
		this.list = list;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public List<Emp> getList() {
		return list;
	}

	public void setList(List<Emp> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "EmpIndexDto [recordCount=" + recordCount + ", list=" + list + "]";
	}
	
}