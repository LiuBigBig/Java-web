package com.icss.hr.dept.pojo;

import java.util.List;

import com.icss.hr.emp.pojo.Emp;

/**
 * 部门实体类
 * 
 * @author Administrator
 *
 */
public class Dept {

	private Integer deptId;

	private String deptName;

	private String deptLoc;

	public Dept() {
		super();
	}

	public Dept(String deptName, String depLoc) {
		super();
		this.deptName = deptName;
		this.deptLoc = depLoc;
	}

	public Dept(Integer deptId, String deptName, String depLoc) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptLoc = depLoc;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptLoc() {
		return deptLoc;
	}

	public void setDeptLoc(String depLoc) {
		this.deptLoc = depLoc;
	}

	@Override
	public String toString() {
		return "Dept [deptId=" + deptId + ", deptName=" + deptName + ", deptLoc=" + deptLoc + "]";
	}	

}