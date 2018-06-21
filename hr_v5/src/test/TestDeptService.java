package test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.dept.pojo.Dept;
import com.icss.hr.dept.service.DeptService;


public class TestDeptService {
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private DeptService service = (DeptService) context.getBean(DeptService.class);
	
	@Test
	public void testAddDept() {
		
		Dept dept = new Dept("ddd","ddd");
		service.addDept(dept);
		
	}
	
	@Test
	public void testUpdateDept() {		
		Dept dept = new Dept(1030,"销售部","北京和平区");
		service.updateDept(dept);		
	}
	
	@Test
	public void testDeleteDept() {		
		service.deleteDept(1090);	
	}
	
	@Test
	public void testQueryDeptById() {		
		Dept dept = service.queryDeptById(10);
		System.out.println(dept);
	}
	
	@Test
	public void testQuery() {		
		List<Dept> list = service.queryDept();
		System.out.println(list);
	}

}