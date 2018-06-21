package com.icss.hr.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.emp.dao.EmpMapper;
import com.icss.hr.emp.pojo.Emp;

/**
 * Ա����������ҵ��
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly=true)
public class BirthdayService {
	
	@Autowired
	private EmpMapper dao;
	
	/**
	 * ��ʱ���Ա����ְ�����Ƿ����
	 */
	public void checkBirthday() {
		
		//��ý�������ְ���ڵ�Ա��
		List<Emp> list = dao.queryByHiredate();
		
		//ѭ���������͵����ʼ���Ա��
		for (Emp emp : list) {
			
			System.out.println("���͵����ʼ���" + emp.getEmpEmail());
			
			//�ʼ�����
			String content = emp.getEmpName() + "�����ã�<br><br> &nbsp;&nbsp;&nbsp;&nbsp;������쵽���²���ȡ��ְ������Ʒ��" 
						+ "<br><br>&nbsp;&nbsp;ף��<br><br>&nbsp;&nbsp;��˾���²�";
			
			//�����ʼ�
			MailUtil.sendMail("zhangsan@163.com", "zhangsan@163.com", "123456",emp.getEmpEmail(),"��˾֪ͨ" , content);
					
		}
		
	}
	

}