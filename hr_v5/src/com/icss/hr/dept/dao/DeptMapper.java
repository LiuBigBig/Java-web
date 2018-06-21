package com.icss.hr.dept.dao;

import java.util.List;
import com.icss.hr.dept.pojo.Dept;

/**
 * ≤ø√≈DAO
 * @author Administrator
 *
 */
public interface DeptMapper {
   
	void insert(Dept dept);
	
	void update(Dept dept);
	
	void delete(Integer deptId);
	
	Dept queryById(Integer deptId);
	
	List<Dept> query();
	
}