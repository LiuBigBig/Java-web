package com.icss.hr.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * ��ɫdao
 * 
 * @author Administrator
 *
 */
public interface RoleMapper {

	/**
	 * ��ѯĳ���û������н�ɫ����
	 * 
	 * @param empLoginName
	 * @return
	 */
	List<Map<String, Object>> queryRole(@Param("empLoginName") String empLoginName);

}