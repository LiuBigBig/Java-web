package com.icss.hr.emp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icss.hr.common.Pager;
import com.icss.hr.emp.pojo.Emp;


/**
 * 员工DAO接口
 * @author Administrator
 *
 */
public interface EmpMapper {
	
	void insert(Emp emp);
	
	void update(Emp emp);
	
	void delete(Integer empId);
	
	Emp queryById(Integer empId);
	
	List<Emp> queryByPage(Pager pager);
	
	int getCount();
	
	Emp queryByName(String empLoginName);
	
	String queryEmpPic(String empLoginName);
	
	void updateEmpPic(@Param("empLoginName")String empLoginName,@Param("empPic") String empPic);
	
	void updateEmpPwd(@Param("empLoginName")String empLoginName,@Param("empPwd")String empPwd);
	
	//按正常条件检索员工
	List<Emp> queryByCondition(@Param("deptId") Integer deptId,@Param("jobId") Integer jobId
			,@Param("empName") String empName,@Param("pager") Pager pager);
	
	//按条件统计总记录数
	int getCountByCondition(@Param("deptId") Integer deptId,@Param("jobId") Integer jobId
			,@Param("empName") String empName);
	
	//返回最后一次生成自增编号
	int getLastInsertId();
	
	//查询返回所有入职日期是今天的员工
	List<Emp> queryByHiredate();

}