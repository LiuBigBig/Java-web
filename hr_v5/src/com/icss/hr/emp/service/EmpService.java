package com.icss.hr.emp.service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.util.Version;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.common.Pager;
import com.icss.hr.emp.dao.EmpMapper;
import com.icss.hr.emp.dto.EmpIndexDto;
import com.icss.hr.emp.index.EmpIndexDao;
import com.icss.hr.emp.pojo.Emp;

/**
 * 员工业务
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmpService {

	@Autowired
	private EmpMapper dao;

	@Autowired
	private EmpIndexDao indexDao;

	/**
	 * 登陆验证
	 * 
	 * @param empLoginName
	 *            用户名
	 * @param empPwd
	 *            密码
	 * @return 登陆验证结果：1 用户名不存在 2 密码错误 3 登陆成功
	 * @throws SQLException
	 */
	@Transactional(readOnly = true)
	public int checkLogin(String empLoginName, String empPwd) {

		Emp emp = dao.queryByName(empLoginName);

		if (emp == null) {
			return 1;
		} else if (!empPwd.equals(emp.getEmpPwd())) {
			return 2;
		}

		return 3;
	}

	/**
	 * 检查登陆名是否存在
	 * 
	 * @param empLoginName
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly = true)
	public boolean checkLoginName(String empLoginName) {

		Emp emp = dao.queryByName(empLoginName);

		if (emp == null)
			return false;

		return true;
	}

	/**
	 * 增加新员工
	 * 
	 * @param emp
	 * @throws IOException
	 * @throws SQLException
	 */
	public void addEmp(Emp emp) throws IOException {
		
		//插入数据
		dao.insert(emp);
		
		//获得自增编号
		int empId = dao.getLastInsertId();
		
		// 增加索引
		Document document = new Document();
		document.add(new TextField("empId", String.valueOf(empId), Store.YES));
		document.add(new TextField("empName", emp.getEmpName(), Store.YES));
		document.add(new TextField("empPhone", emp.getEmpPhone(), Store.YES));
		document.add(new TextField("empInfo", emp.getEmpInfo(), Store.YES));

		indexDao.create(document);
	}

	/**
	 * 通过id查询单个员工数据
	 * 
	 * @param empId
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly = true)
	public Emp queryEmpById(Integer empId) {

		return dao.queryById(empId);
	}

	/**
	 * 返回员工总记录数
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly = true)
	public int getEmpCount() {

		return dao.getCount();
	}

	/**
	 * 分页查询员工数据
	 * 
	 * @param pager
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly = true)
	public List<Emp> queryEmpByPage(Pager pager) {

		return dao.queryByPage(pager);
	}

	/**
	 * 修改员工
	 * 
	 * @param emp
	 * @throws IOException
	 * @throws SQLException
	 */
	public void updateEmp(Emp emp) throws IOException {

		dao.update(emp);

		// 修改索引
		Document document = new Document();
		document.add(new TextField("empId", String.valueOf(emp.getEmpId()), Store.YES));
		document.add(new TextField("empName", emp.getEmpName(), Store.YES));
		document.add(new TextField("empPhone", emp.getEmpPhone(), Store.YES));
		document.add(new TextField("empInfo", emp.getEmpInfo(), Store.YES));

		Term term = new Term("empId", String.valueOf(emp.getEmpId()));

		indexDao.update(term, document);
	}

	/**
	 * 删除员工
	 * 
	 * @param empId
	 * @throws IOException
	 * @throws SQLException
	 */
	public void deleteEmp(Integer empId) throws IOException {

		dao.delete(empId);

		// 删除索引
		Term term = new Term("empId", String.valueOf(empId));

		indexDao.delete(term);
	}

	/**
	 * 查询返回员工头像数据
	 * 
	 * @param empLoginName
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly = true)
	public String queryEmpPic(String empLoginName) {

		return dao.queryEmpPic(empLoginName);
	}

	/**
	 * 更新员工头像
	 * 
	 * @param empLoginName
	 * @param empPic
	 * @throws SQLException
	 */
	public void updateEmpPic(String empLoginName, String empPic) {

		dao.updateEmpPic(empLoginName, empPic);
	}

	/**
	 * 修改员工密码
	 * 
	 * @param empLoginName
	 * @param empPwd
	 * @throws SQLException
	 */
	public void updateEmpPwd(String empLoginName, String empPwd) {

		dao.updateEmpPwd(empLoginName, empPwd);
	}

	/**
	 * 查询当前密码
	 * 
	 * @param empLoginName
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly = true)
	public String queryEmpPwd(String empLoginName) {

		return dao.queryByName(empLoginName).getEmpPwd();
	}

	/**
	 * 条件分页查询
	 */
	@Transactional(readOnly = true)
	public List<Emp> queryEmpByCondition(Integer deptId, Integer jobId, String empName, Pager pager) {

		return dao.queryByCondition(deptId, jobId, empName, pager);
	}

	/**
	 * 根据条件返回总记录数
	 */
	@Transactional(readOnly = true)
	public int getEmpCountByCondition(Integer deptId, Integer jobId, String empName) {

		return dao.getCountByCondition(deptId, jobId, empName);
	}

	/**
	 * 全文检索数据
	 * @throws ParseException 
	 * @throws InvalidTokenOffsetsException 
	 * @throws IOException 
	 */
	public EmpIndexDto queryEmpByIndex(String queryStr) throws ParseException, IOException, InvalidTokenOffsetsException {

		// 中文分词器
		Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_47);

		// 把检索字符串转换为query对象，{"name","content","path"}就是field的字段名称
		QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_47, new String[] { "empName", "empPhone", "empInfo" },
				analyzer);
		Query query = parser.parse(queryStr);
		
		EmpIndexDto dto = indexDao.search(query);

		return dto;
	}

	/**
	 * 导出Excel报表功能
	 * @throws IOException 
	 */
	public void exportExcel(List<Emp> list,OutputStream out) throws IOException {
		
//		工作簿对象
		HSSFWorkbook wb = new HSSFWorkbook();
		
//		工作表对象
		HSSFSheet sheet1 = wb.createSheet("员工数据");
		
//		默认列宽
		sheet1.setDefaultColumnWidth(15);
		
		//写入标题行
		HSSFRow titleRow = sheet1.createRow(0);
		
		String[] titles = {"员工编号","姓名","邮箱","电话","部门","职务"};
		
		for (int i = 0;i < titles.length;i ++) {
			HSSFCell cell = titleRow.createCell(i);
			cell.setCellValue(titles[i]);
		}
		
		//写入数据行
		for (int i = 0;i < list.size();i ++) {
			
			HSSFRow row = sheet1.createRow(i + 1);
			
			row.createCell(0).setCellValue( list.get(i).getEmpId());
			row.createCell(1).setCellValue( list.get(i).getEmpName());
			row.createCell(2).setCellValue( list.get(i).getEmpEmail());
			row.createCell(3).setCellValue( list.get(i).getEmpPhone());
			row.createCell(4).setCellValue( list.get(i).getDept().getDeptName());
			row.createCell(5).setCellValue( list.get(i).getJob().getJobName());
		}
		
		//写入到输出流中
		wb.write(out);
		
		out.close();
	}
}