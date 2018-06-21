package test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.job.pojo.Job;
import com.icss.hr.job.service.JobService;

/**
 * 测试职务dao
 * @author Administrator
 *
 */
public class TestJobService {
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private JobService service = (JobService) context.getBean(JobService.class);
	
	@Test
	public void testInsert() {
		
		Job job = new Job("总经理",15000,36000);
		service.addJob(job);
		
	}
	
	@Test
	public void testUpdate() {
		
		Job job = new Job(81,"CEO",18000,39000);
		service.updateJob(job);
		
	}
	
	@Test
	public void testDelete() {
		
		service.deleteJob(81);
		
	}
	
	@Test
	public void testQueryById() {
		
		Job job = service.queryJobById(48);
		System.out.println(job);
		
	}
	
	@Test
	public void testQuery() {
		
		List<Job> list = service.queryJob();
		System.out.println(list);
		
	}
		
}