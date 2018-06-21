package test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.analyze.dto.DeptEmpCount;
import com.icss.hr.analyze.dto.DeptSal;
import com.icss.hr.analyze.service.AnaService;

/**
 * ≤‚ ‘ ˝æ›∑÷Œˆ
 * @author Administrator
 *
 */
public class TestAnaService {
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private AnaService service = context.getBean(AnaService.class);
	
	@Test
	public void testQueryEmpCount() throws SQLException {
		
		List<DeptEmpCount> list = service.queryEmpCount();
		
		System.out.println(list);		
	}
	
	@Test
	public void testQueryJobAvgSal() throws SQLException {
		
		List<Map<String, Object>> list = service.queryJobAvgSal();
		
		System.out.println(list);
		
	}
	
	@Test
	public void testQueryDeptSal() throws SQLException {
		
		List<DeptSal> list = service.queryDeptSal();
		
		System.out.println(list);
		
	}

}