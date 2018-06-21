package com.icss.hr.analyze.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.analyze.dao.AnaMapper;
import com.icss.hr.analyze.dto.DeptEmpCount;
import com.icss.hr.analyze.dto.DeptSal;

/**
 * 数据分析业务
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly=true)
public class AnaService {

	@Autowired
	private AnaMapper dao;

	public List<DeptEmpCount> queryEmpCount() {

		return dao.queryEmpCount();
	}

	public List<Map<String, Object>> queryJobAvgSal()  {

		return dao.queryJobAvgSal();
	}

	public List<DeptSal> queryDeptSal() {

		return dao.queryDeptSal();
	}

}