package com.icss.hr.dept.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icss.hr.dept.pojo.Dept;
import com.icss.hr.dept.service.DeptService;

/**
 * 部门控制器
 * @author Administrator
 *
 */
@Controller
public class DeptController {
	
	@Autowired
	private DeptService service;
	
	/**
	 * 增加部门
	 * @param dept
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dept/add")
	public void addDept(Dept dept,HttpServletRequest request,HttpServletResponse response) {
		service.addDept(dept);				
	}
	
	/**
	 * 查询部门
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dept/query")
	public List<Dept> queryDept(HttpServletRequest request,HttpServletResponse response) {
		
		return service.queryDept();
	}	
	
	/**
	 * 删除部门
	 * @param deptId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dept/delete")
	public void deleteDept(Integer deptId,HttpServletRequest request,HttpServletResponse response) {
		
		service.deleteDept(deptId);
	}
	
	/**
	 * 通过id查询部门
	 * @param deptId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dept/get")
	public Dept getDept(Integer deptId,HttpServletRequest request,HttpServletResponse response) {
		
		return service.queryDeptById(deptId);
	}
	
	/**
	 * 修改部门
	 * @param dept
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dept/update")
	public void updateDept(Dept dept,HttpServletRequest request,HttpServletResponse response) {
		
		service.updateDept(dept);
	}

}