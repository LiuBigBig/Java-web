package test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.perm.service.PermService;


public class TestPermService {
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private PermService service =  context.getBean(PermService.class);
	
	@Test
	public void testQueryRole() {
		
		List<Map<String,Object>> list = service.queryRole("zhangsan");
		
		for (Map map : list) {
			System.out.println(map);
		}
		
	}
	
	@Test
	public void testQueryPerm() {
		
		List<Map<String,Object>> list = service.queryPerm("zhangsan");
		
		for (Map map : list) {
			System.out.println(map);
		}
		
	}
		
}