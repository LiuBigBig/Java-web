package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.FileCopyUtils;

import com.icss.hr.common.Pager;
import com.icss.hr.pic.pojo.Pic;
import com.icss.hr.pic.service.PicService;

/**
 * ≤‚ ‘Õºø‚Dao
 * @author Administrator
 *
 */
public class TestPicService {
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private PicService service = context.getBean(PicService.class);	
	
	@Test
	public void testInsert() throws Exception {
		
		File file = new File("e:\\1.jpg");
		
		FileInputStream fis = new FileInputStream(file);
		
		byte[] picData = FileCopyUtils.copyToByteArray(file);
		
		Pic pic = new Pic("1.jpg", "Œ‚“‡∑≤’’∆¨", file.length(), "¿ÓÀƒ", picData, new Date());
		
		service.addPic(pic);				
	}
	
	@Test
	public void testQueryByPage() throws SQLException {
		
		Pager pager = new Pager(service.getPicCount(),7,3);
		
		List<Pic> list = service.queryPicByPage(pager);
		
		for (Pic pic : list) {
			System.out.println(pic);
		}
	}
	
	@Test
	public void testDelete() throws SQLException {
		
		service.deletePic(81);
		
	}
	
	@Test
	public void testQueryById() throws SQLException, IOException {
		
		Pic pic = service.queryPicById(3);
		System.out.println(pic);
				
	}

}