package com.icss.hr.job.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.job.dao.JobMapper;
import com.icss.hr.job.pojo.Job;

/**
 * 职务业务
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class JobService {
	
	@Autowired
	private JobMapper dao;
	
	public void addJob(Job job) {
		dao.insert(job);
	}
	
	public void updateJob(Job job)  {
		dao.update(job);
	}
	
	public void deleteJob(Integer jobId) {
		dao.delete(jobId);
	}
	
	@Transactional(readOnly=true)
	public Job queryJobById(Integer jobId)  {
		
		return dao.queryById(jobId);
	}
	
	@Transactional(readOnly=true)
	public List<Job> queryJob() {
		
		return dao.query();
	}

}