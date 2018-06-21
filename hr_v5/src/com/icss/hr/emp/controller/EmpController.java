package com.icss.hr.emp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icss.hr.common.Pager;
import com.icss.hr.emp.dto.EmpIndexDto;
import com.icss.hr.emp.pojo.Emp;
import com.icss.hr.emp.service.EmpService;

/**
 * 员工控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class EmpController {

	@Autowired
	private EmpService service;

	/**
	 * 增加员工
	 * 
	 * @param emp
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/emp/add")
	public void addEmp(Emp emp, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//密码加密
		String empPwd = new Sha256Hash(emp.getEmpPwd(),"icss",10).toBase64();
		emp.setEmpPwd(empPwd);
		
		service.addEmp(emp);
	}

	/**
	 * 分页查询员工
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/emp/query")
	public Map<String, Object> queryEmp(String pageNum, String pageSize, HttpServletRequest request,
			HttpServletResponse response) {

		int pageNumInt = 1;

		try {
			pageNumInt = Integer.parseInt(pageNum);
		} catch (Exception e) {

		}

		int pageSizeInt = 10;

		try {
			pageSizeInt = Integer.parseInt(pageSize);
		} catch (Exception e) {

		}

		Pager pager = new Pager(service.getEmpCount(), pageSizeInt, pageNumInt);
		List<Emp> list = service.queryEmpByPage(pager);

		HashMap<String, Object> map = new HashMap<>();
		map.put("pager", pager);
		map.put("list", list);

		return map;
	}

	/**
	 * 删除员工
	 * 
	 * @param empId
	 * @throws IOException 
	 */
	@RequestMapping("/emp/delete")
	public void deleteEmp(Integer empId,HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		service.deleteEmp(empId);

	}

	/**
	 * 返回指定id的员工
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/emp/get")
	public Emp queryEmpById(Integer empId,HttpServletRequest request,
			HttpServletResponse response) {

		return service.queryEmpById(empId);
	}

	/**
	 * 修改员工
	 * @throws IOException 
	 */
	@RequestMapping("/emp/update")
	public void updateEmp(Emp emp, HttpServletRequest request, HttpServletResponse response) throws IOException {

		service.updateEmp(emp);
	}
	
	/**
	 * 判断登录名是否存在
	 */
	@ResponseBody
	@RequestMapping("/emp/checkLoginName")
	public boolean checkLoginName(String empLoginName,HttpServletRequest request, HttpServletResponse response) {
		
		return service.checkLoginName(empLoginName);		
	}
	
	/**
	 * 登陆验证
	 */
	@ResponseBody
	@RequestMapping("/emp/login")
	public int checkLogin(String empLoginName,String empPwd,HttpServletRequest request, HttpServletResponse response) {
		
		//按照Sha256算法进行密码加密
		empPwd = new Sha256Hash(empPwd,"icss",10).toBase64();
		
		//封装用户名和密码
		UsernamePasswordToken token = new UsernamePasswordToken(empLoginName,empPwd);
		
		//设置RememberMe（以cookie形式把用户名记录在浏览器端）
		token.setRememberMe(true);
		
		//Shiro登陆
		Subject subject = SecurityUtils.getSubject();
		
		try {
			subject.login(token);
		} catch (UnknownAccountException e) { //用户名不存在
			return 1;
		} catch (IncorrectCredentialsException e) { //密码错误
			return 2; 
		}
		
		//在session存储登陆标识
		HttpSession session = request.getSession();
		session.setAttribute("empLoginName", empLoginName);
		
		return 3;		
	}		
	
	/**
	 * 获得当前用户的头像数据
	 */
	@ResponseBody
	@RequestMapping("/emp/getEmpPic")
	public String getEmpPic(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		
		String empPic = service.queryEmpPic(empLoginName);
		return empPic;
	}
	
	/**
	 * 更新头像
	 */
	@RequestMapping("/emp/updateEmpPic")
	public void updateEmpPic(String imgBase64,HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		
		service.updateEmpPic(empLoginName, imgBase64);
	}
	
	/**
	 * 返回当前用户的密码
	 */
	@ResponseBody
	@RequestMapping("/emp/getEmpPwd")
	public String getEmpPwd(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		
		String empPwd = service.queryEmpPwd(empLoginName);
		return empPwd;
	}		
	
	/**
	 * 修改密码
	 */
	@RequestMapping("/emp/updateEmpPwd")
	public void updateEmpPwd(String empPwd,HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		
		//密码加密
		empPwd = new Sha256Hash(empPwd,"icss",10).toBase64();
		
		service.updateEmpPwd(empLoginName, empPwd);		
	}
	
	/**
	 * 条件分页查询员工
	 */
	@ResponseBody
	@RequestMapping("/emp/queryByCondition")
	public Map<String, Object> queryByCondition(String deptId,String jobId,String empName,
			String pageNum,String pageSize,HttpServletRequest request, HttpServletResponse response) {
		
		Integer deptIdInt = null;
		
		try {
			deptIdInt = Integer.parseInt(deptId);
		} catch (Exception e) {
			
		}
		
		Integer jobIdInt = null;
		
		try {
			jobIdInt = Integer.parseInt(jobId);
		} catch (Exception e) {
			
		}
		
		int pageNumInt = 1;

		try {
			pageNumInt = Integer.parseInt(pageNum);
		} catch (Exception e) {

		}

		int pageSizeInt = 10;

		try {
			pageSizeInt = Integer.parseInt(pageSize);
		} catch (Exception e) {

		}
		
		int recordCount = service.getEmpCountByCondition(deptIdInt, jobIdInt, empName);
		
		Pager pager = new Pager(recordCount, pageSizeInt, pageNumInt);		
		List<Emp> list = service.queryEmpByCondition(deptIdInt, jobIdInt, empName, pager);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("pager", pager);
		map.put("list", list);

		return map;
	}
	
	/**
	 * 全文检索员工
	 * @throws InvalidTokenOffsetsException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping("/emp/queryByIndex")
	public EmpIndexDto queryByIndex(String queryStr,HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, InvalidTokenOffsetsException {
		
		return service.queryEmpByIndex(queryStr);		
	}

	/**
	 * 验证用户旧密码
	 */
	@ResponseBody
	@RequestMapping("/emp/checkEmpPwd")
	public boolean checkEmpPwd(String empPwd,HttpServletRequest request, HttpServletResponse response) {
		
		//session中获得当前用户名
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		
		//查询当前密码
		String oldEmpPwd = service.queryEmpPwd(empLoginName);
		
		//密码加密
		empPwd = new Sha256Hash(empPwd,"icss",10).toBase64();
		
		if (oldEmpPwd.equals(empPwd)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 导出excel报表
	 * @throws IOException 
	 */
	@RequestMapping("/emp/export")
	public void export(String deptId,String jobId,String empName,
			String pageNum,String pageSize,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//中文文件名转码
		String filename = new String("员工数据.xls".getBytes(),"iso-8859-1");
		
		//设置报头，通知浏览器以附件形式接收数据
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		
		Integer deptIdInt = null;
		
		try {
			deptIdInt = Integer.parseInt(deptId);
		} catch (Exception e) {
			
		}
		
		Integer jobIdInt = null;
		
		try {
			jobIdInt = Integer.parseInt(jobId);
		} catch (Exception e) {
			
		}
		
		int recordCount = service.getEmpCount();
		
		Pager pager = new Pager(service.getEmpCount(), recordCount, 1);		
		List<Emp> list = service.queryEmpByCondition(deptIdInt, jobIdInt, empName, pager);
		
		//调用导出功能
		service.exportExcel(list, response.getOutputStream());
		
	}
}