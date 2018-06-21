package com.icss.hr.analyze.dto;

/**
 * 部门最低最高工资DTO
 * @author Administrator
 *
 */
public class DeptSal {

	private String deptName;
	private Integer jobMaxSal;
	private Integer jobMinSal;
	
	public DeptSal() {
		super();
	}

	public DeptSal(String deptName, Integer jobMaxSal, Integer jobMinSal) {
		super();
		this.deptName = deptName;
		this.jobMaxSal = jobMaxSal;
		this.jobMinSal = jobMinSal;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getJobMaxSal() {
		return jobMaxSal;
	}

	public void setJobMaxSal(Integer jobMaxSal) {
		this.jobMaxSal = jobMaxSal;
	}

	public Integer getJobMinSal() {
		return jobMinSal;
	}

	public void setJobMinSal(Integer jobMinSal) {
		this.jobMinSal = jobMinSal;
	}

	@Override
	public String toString() {
		return "DeptSal [deptName=" + deptName + ", jobMaxSal=" + jobMaxSal + ", jobMinSal=" + jobMinSal + "]";
	}
	
	
	
}
