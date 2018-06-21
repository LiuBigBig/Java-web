package com.icss.hr.analyze.dao;

import java.util.List;
import java.util.Map;

import com.icss.hr.analyze.dto.DeptEmpCount;
import com.icss.hr.analyze.dto.DeptSal;

public interface AnaMapper {
	
	/**
	 * 部门的人数
	 * @return
	 */
	List<DeptEmpCount> queryEmpCount();
	
	/**
	 * 职务的平均工资
	 */
	List<Map<String, Object>> queryJobAvgSal();
	
	/**
	 * 部门最高最低工资
	 */
	List<DeptSal> queryDeptSal();

}