package com.icss.hr.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.emp.dao.EmpMapper;
import com.icss.hr.emp.pojo.Emp;

/**
 * 员工生日提醒业务
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly=true)
public class BirthdayService {
	
	@Autowired
	private EmpMapper dao;
	
	/**
	 * 定时检查员工入职日期是否今天
	 */
	public void checkBirthday() {
		
		//获得今天是入职日期的员工
		List<Emp> list = dao.queryByHiredate();
		
		//循环遍历发送电子邮件给员工
		for (Emp emp : list) {
			
			System.out.println("发送电子邮件：" + emp.getEmpEmail());
			
			//邮件内容
			String content = emp.getEmpName() + "，您好！<br><br> &nbsp;&nbsp;&nbsp;&nbsp;请与今天到人事部领取入职纪念礼品！" 
						+ "<br><br>&nbsp;&nbsp;祝好<br><br>&nbsp;&nbsp;公司人事部";
			
			//发送邮件
			MailUtil.sendMail("zhangsan@163.com", "zhangsan@163.com", "123456",emp.getEmpEmail(),"公司通知" , content);
					
		}
		
	}
	

}