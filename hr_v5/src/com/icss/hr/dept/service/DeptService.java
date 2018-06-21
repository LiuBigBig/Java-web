package com.icss.hr.dept.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.dept.dao.DeptMapper;
import com.icss.hr.dept.pojo.Dept;

/**
 * 部门业务
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class DeptService {
	
	@Autowired
	private DeptMapper dao;
	
	/**
	 * 增加部门
	 * @throws SQLException 
	 */
	public void addDept(Dept dept) {		
		dao.insert(dept);
	}
	
	/**
	 * 修改部门
	 * @param dept
	 * @throws SQLException
	 */
	public void updateDept(Dept dept) {
		dao.update(dept);
	}
	
	/**
	 * 删除部门
	 * @param deptId
	 * @throws SQLException
	 */
	public void deleteDept(Integer deptId) {
		dao.delete(deptId);
	}
	
	/**
	 * 返回单个部门数据
	 * @param deptId
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly=true)
	public Dept queryDeptById(Integer deptId) {
		
		return dao.queryById(deptId);
	}
	
	/**
	 * 返回部门所有数据
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly=true)
	public List<Dept> queryDept() {
		
		return dao.query();
	}

}