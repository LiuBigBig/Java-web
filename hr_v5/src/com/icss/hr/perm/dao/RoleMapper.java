package com.icss.hr.perm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 角色dao
 * 
 * @author Administrator
 *
 */
public interface RoleMapper {

	/**
	 * 查询某个用户的所有角色集合
	 * 
	 * @param empLoginName
	 * @return
	 */
	List<Map<String, Object>> queryRole(@Param("empLoginName") String empLoginName);

}