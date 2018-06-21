package com.icss.hr.pic.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.common.Pager;
import com.icss.hr.pic.dao.PicMapper;
import com.icss.hr.pic.pojo.Pic;

/**
 * Í¼¿âÒµÎñ
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class PicService {
	
	@Autowired
	private PicMapper dao;
	
	public void addPic(Pic pic) {
		dao.insert(pic);
	}
	
	public void deletePic(Integer picId) {
		dao.delete(picId);
	}
	
	@Transactional(readOnly=true)
	public List<Pic> queryPicByPage(Pager pager) {
		
		return dao.queryByPage(pager);
	}
	
	@Transactional(readOnly=true)
	public Pic queryPicById(Integer picId) {
	
		return dao.queryById(picId);
	}
	
	@Transactional(readOnly=true)
	public int getPicCount() {
		
		return dao.getCount();
	}
}