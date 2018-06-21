package com.icss.hr.common;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * ���͵����ʼ�������
 * @author Administrator
 *
 */
public class MailUtil {
	
	/**
	 * ���͵����ʼ�
	 * @param username ���ͷ����ʼ���ַ
	 * @param from ���ͷ����û���
	 * @param userPwd ���ͷ�������
	 * @param to ���շ������ʼ���ַ
	 * @param title �ʼ�����
	 * @param content �ʼ�����
	 */
	public static void sendMail(String username,String from,String userPwd,String to,String title,String content) {
		
		//���ò���
				Properties props=new Properties();
				props.setProperty("mail.smtp.auth", "true");//�Ƿ��������֤
				props.setProperty("mail.transport.protocol", "smtp");//ʹ�õĴ���Э��
				
				//�����ʼ�����������
				Session session=Session.getInstance(props);
				
				//��ʾ������Ϣ
				session.setDebug(true);
						
				//��Ϣ����
				Message msg = new MimeMessage(session);
				
				try {			
					msg.setFrom(new InternetAddress(username));//���ͷ��������ַ
					msg.setSubject(title);//����
					//msg.setText("���");//��ͨ�ı�����
					msg.setContent(content,"text/html;charset=gbk;");
					
					//�������
					Transport transport=session.getTransport();	
					
					//����smtp�������Ͷ˿��Լ��˺ź�����
					transport.connect("localhost",25,from,userPwd);
					
					//���շ��������ַ
					transport.sendMessage(msg,new Address[]{new InternetAddress(to)});
														
					transport.close();
				} catch (MessagingException e) {			
					e.printStackTrace();
				}
		
	}	

}
