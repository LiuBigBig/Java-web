package com.icss.hr.common;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.icss.hr.emp.service.EmpService;
import com.icss.hr.perm.service.PermService;

/**
 * Shiro��ȫ���Realm�࣬�����½��֤��Ȩ�޲�ѯ
 * @author Administrator
 *
 */
public class ShiroRealm extends AuthorizingRealm {
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private PermService permService;

	/**
	 * ��õ�ǰ�û�����Ȩ��Ϣ
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		
		//��õ�ǰ�û���
		String empLoginName = (String) getAvailablePrincipal(principalCollection);
		
		System.out.println("������Ȩ...." + empLoginName);
		
		// ͨ���û���ȥ����û���������Դ��������Դ����info��
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//���ý�ɫ
		HashSet<String> roleSet = new HashSet<>();		
		List<Map<String, Object>> roleList = permService.queryRole(empLoginName);
		
		for (Map map : roleList) {
			roleSet.add((String)map.get("role_name"));
		}
		
		info.setRoles(roleSet);
		
		//����Ȩ��
		HashSet<String> permSet = new HashSet<>();
		List<Map<String, Object>> permList = permService.queryPerm(empLoginName);
	
		for (Map map : permList) {
			permSet.add((String)map.get("perm_name"));
		}
		
		info.setStringPermissions(permSet);
		
		return info;
	}

	/**
	 * ��õ�ǰ�û�����֤��Ϣ����½��
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		System.out.println("���е�½��֤...doGetAuthenticationInfo");

		// token�д�����������û���������
		UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
		
		String empLoginName = upToken.getUsername();
		String empPwd = String.valueOf(upToken.getPassword());

		//��֤��½
		int result = empService.checkLogin(empLoginName, empPwd);
		
		if (result == 1) {
			//�û������� 
			throw new UnknownAccountException();
		} else if (result == 2) {
			//�������
			throw new IncorrectCredentialsException();
		} else {
			// �ȶԳɹ��򷵻�info���ȶ�ʧ�����׳���Ӧ��Ϣ���쳣AuthenticationException
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(empLoginName,
					empPwd.toCharArray(), getName());
			
			return info;
		}
				
	}
	
}