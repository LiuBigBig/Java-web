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
 * Ա��������
 * 
 * @author Administrator
 *
 */
@Controller
public class EmpController {

	@Autowired
	private EmpService service;

	/**
	 * ����Ա��
	 * 
	 * @param emp
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/emp/add")
	public void addEmp(Emp emp, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//�������
		String empPwd = new Sha256Hash(emp.getEmpPwd(),"icss",10).toBase64();
		emp.setEmpPwd(empPwd);
		
		service.addEmp(emp);
	}

	/**
	 * ��ҳ��ѯԱ��
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
	 * ɾ��Ա��
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
	 * ����ָ��id��Ա��
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
	 * �޸�Ա��
	 * @throws IOException 
	 */
	@RequestMapping("/emp/update")
	public void updateEmp(Emp emp, HttpServletRequest request, HttpServletResponse response) throws IOException {

		service.updateEmp(emp);
	}
	
	/**
	 * �жϵ�¼���Ƿ����
	 */
	@ResponseBody
	@RequestMapping("/emp/checkLoginName")
	public boolean checkLoginName(String empLoginName,HttpServletRequest request, HttpServletResponse response) {
		
		return service.checkLoginName(empLoginName);		
	}
	
	/**
	 * ��½��֤
	 */
	@ResponseBody
	@RequestMapping("/emp/login")
	public int checkLogin(String empLoginName,String empPwd,HttpServletRequest request, HttpServletResponse response) {
		
		//����Sha256�㷨�����������
		empPwd = new Sha256Hash(empPwd,"icss",10).toBase64();
		
		//��װ�û���������
		UsernamePasswordToken token = new UsernamePasswordToken(empLoginName,empPwd);
		
		//����RememberMe����cookie��ʽ���û�����¼��������ˣ�
		token.setRememberMe(true);
		
		//Shiro��½
		Subject subject = SecurityUtils.getSubject();
		
		try {
			subject.login(token);
		} catch (UnknownAccountException e) { //�û���������
			return 1;
		} catch (IncorrectCredentialsException e) { //�������
			return 2; 
		}
		
		//��session�洢��½��ʶ
		HttpSession session = request.getSession();
		session.setAttribute("empLoginName", empLoginName);
		
		return 3;		
	}		
	
	/**
	 * ��õ�ǰ�û���ͷ������
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
	 * ����ͷ��
	 */
	@RequestMapping("/emp/updateEmpPic")
	public void updateEmpPic(String imgBase64,HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		
		service.updateEmpPic(empLoginName, imgBase64);
	}
	
	/**
	 * ���ص�ǰ�û�������
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
	 * �޸�����
	 */
	@RequestMapping("/emp/updateEmpPwd")
	public void updateEmpPwd(String empPwd,HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		
		//�������
		empPwd = new Sha256Hash(empPwd,"icss",10).toBase64();
		
		service.updateEmpPwd(empLoginName, empPwd);		
	}
	
	/**
	 * ������ҳ��ѯԱ��
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
	 * ȫ�ļ���Ա��
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
	 * ��֤�û�������
	 */
	@ResponseBody
	@RequestMapping("/emp/checkEmpPwd")
	public boolean checkEmpPwd(String empPwd,HttpServletRequest request, HttpServletResponse response) {
		
		//session�л�õ�ǰ�û���
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		
		//��ѯ��ǰ����
		String oldEmpPwd = service.queryEmpPwd(empLoginName);
		
		//�������
		empPwd = new Sha256Hash(empPwd,"icss",10).toBase64();
		
		if (oldEmpPwd.equals(empPwd)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * ����excel����
	 * @throws IOException 
	 */
	@RequestMapping("/emp/export")
	public void export(String deptId,String jobId,String empName,
			String pageNum,String pageSize,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//�����ļ���ת��
		String filename = new String("Ա������.xls".getBytes(),"iso-8859-1");
		
		//���ñ�ͷ��֪ͨ������Ը�����ʽ��������
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
		
		//���õ�������
		service.exportExcel(list, response.getOutputStream());
		
	}
}