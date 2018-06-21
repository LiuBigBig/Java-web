package com.icss.hr.perm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.perm.dao.PermissionMapper;
import com.icss.hr.perm.dao.RoleMapper;

/**
 * 权限业务
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class PermService {
	
	@Autowired
	private RoleMapper roleDao;
	
	@Autowired
	private PermissionMapper permDao;
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryRole(String empLoginName) {
		
		return roleDao.queryRole(empLoginName);		
	}
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryPerm(String empLoginName) {
		
		return permDao.queryPerm(empLoginName);
	}
	
}