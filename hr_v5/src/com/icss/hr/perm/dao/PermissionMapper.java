package com.icss.hr.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 权限Dao
 * @author Administrator
 *
 */
public interface PermissionMapper {
	
	/**
	 * 查询某个用户的所有权限集合
	 * 
	 * @param empLoginName
	 * @return
	 */
	List<Map<String, Object>> queryPerm(@Param("empLoginName") String empLoginName);

}