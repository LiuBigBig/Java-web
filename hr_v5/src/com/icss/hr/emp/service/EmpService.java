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
 * Ա��ҵ��
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
	 * ��½��֤
	 * 
	 * @param empLoginName
	 *            �û���
	 * @param empPwd
	 *            ����
	 * @return ��½��֤�����1 �û��������� 2 ������� 3 ��½�ɹ�
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
	 * ����½���Ƿ����
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
	 * ������Ա��
	 * 
	 * @param emp
	 * @throws IOException
	 * @throws SQLException
	 */
	public void addEmp(Emp emp) throws IOException {
		
		//��������
		dao.insert(emp);
		
		//����������
		int empId = dao.getLastInsertId();
		
		// ��������
		Document document = new Document();
		document.add(new TextField("empId", String.valueOf(empId), Store.YES));
		document.add(new TextField("empName", emp.getEmpName(), Store.YES));
		document.add(new TextField("empPhone", emp.getEmpPhone(), Store.YES));
		document.add(new TextField("empInfo", emp.getEmpInfo(), Store.YES));

		indexDao.create(document);
	}

	/**
	 * ͨ��id��ѯ����Ա������
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
	 * ����Ա���ܼ�¼��
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Transactional(readOnly = true)
	public int getEmpCount() {

		return dao.getCount();
	}

	/**
	 * ��ҳ��ѯԱ������
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
	 * �޸�Ա��
	 * 
	 * @param emp
	 * @throws IOException
	 * @throws SQLException
	 */
	public void updateEmp(Emp emp) throws IOException {

		dao.update(emp);

		// �޸�����
		Document document = new Document();
		document.add(new TextField("empId", String.valueOf(emp.getEmpId()), Store.YES));
		document.add(new TextField("empName", emp.getEmpName(), Store.YES));
		document.add(new TextField("empPhone", emp.getEmpPhone(), Store.YES));
		document.add(new TextField("empInfo", emp.getEmpInfo(), Store.YES));

		Term term = new Term("empId", String.valueOf(emp.getEmpId()));

		indexDao.update(term, document);
	}

	/**
	 * ɾ��Ա��
	 * 
	 * @param empId
	 * @throws IOException
	 * @throws SQLException
	 */
	public void deleteEmp(Integer empId) throws IOException {

		dao.delete(empId);

		// ɾ������
		Term term = new Term("empId", String.valueOf(empId));

		indexDao.delete(term);
	}

	/**
	 * ��ѯ����Ա��ͷ������
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
	 * ����Ա��ͷ��
	 * 
	 * @param empLoginName
	 * @param empPic
	 * @throws SQLException
	 */
	public void updateEmpPic(String empLoginName, String empPic) {

		dao.updateEmpPic(empLoginName, empPic);
	}

	/**
	 * �޸�Ա������
	 * 
	 * @param empLoginName
	 * @param empPwd
	 * @throws SQLException
	 */
	public void updateEmpPwd(String empLoginName, String empPwd) {

		dao.updateEmpPwd(empLoginName, empPwd);
	}

	/**
	 * ��ѯ��ǰ����
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
	 * ������ҳ��ѯ
	 */
	@Transactional(readOnly = true)
	public List<Emp> queryEmpByCondition(Integer deptId, Integer jobId, String empName, Pager pager) {

		return dao.queryByCondition(deptId, jobId, empName, pager);
	}

	/**
	 * �������������ܼ�¼��
	 */
	@Transactional(readOnly = true)
	public int getEmpCountByCondition(Integer deptId, Integer jobId, String empName) {

		return dao.getCountByCondition(deptId, jobId, empName);
	}

	/**
	 * ȫ�ļ�������
	 * @throws ParseException 
	 * @throws InvalidTokenOffsetsException 
	 * @throws IOException 
	 */
	public EmpIndexDto queryEmpByIndex(String queryStr) throws ParseException, IOException, InvalidTokenOffsetsException {

		// ���ķִ���
		Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_47);

		// �Ѽ����ַ���ת��Ϊquery����{"name","content","path"}����field���ֶ�����
		QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_47, new String[] { "empName", "empPhone", "empInfo" },
				analyzer);
		Query query = parser.parse(queryStr);
		
		EmpIndexDto dto = indexDao.search(query);

		return dto;
	}

	/**
	 * ����Excel������
	 * @throws IOException 
	 */
	public void exportExcel(List<Emp> list,OutputStream out) throws IOException {
		
//		����������
		HSSFWorkbook wb = new HSSFWorkbook();
		
//		���������
		HSSFSheet sheet1 = wb.createSheet("Ա������");
		
//		Ĭ���п�
		sheet1.setDefaultColumnWidth(15);
		
		//д�������
		HSSFRow titleRow = sheet1.createRow(0);
		
		String[] titles = {"Ա�����","����","����","�绰","����","ְ��"};
		
		for (int i = 0;i < titles.length;i ++) {
			HSSFCell cell = titleRow.createCell(i);
			cell.setCellValue(titles[i]);
		}
		
		//д��������
		for (int i = 0;i < list.size();i ++) {
			
			HSSFRow row = sheet1.createRow(i + 1);
			
			row.createCell(0).setCellValue( list.get(i).getEmpId());
			row.createCell(1).setCellValue( list.get(i).getEmpName());
			row.createCell(2).setCellValue( list.get(i).getEmpEmail());
			row.createCell(3).setCellValue( list.get(i).getEmpPhone());
			row.createCell(4).setCellValue( list.get(i).getDept().getDeptName());
			row.createCell(5).setCellValue( list.get(i).getJob().getJobName());
		}
		
		//д�뵽�������
		wb.write(out);
		
		out.close();
	}
}