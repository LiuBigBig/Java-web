package com.icss.hr.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Ȩ��Dao
 * @author Administrator
 *
 */
public interface PermissionMapper {
	
	/**
	 * ��ѯĳ���û�������Ȩ�޼���
	 * 
	 * @param empLoginName
	 * @return
	 */
	List<Map<String, Object>> queryPerm(@Param("empLoginName") String empLoginName);

}