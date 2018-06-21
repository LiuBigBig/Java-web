package com.icss.hr.emp.index;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.icss.hr.emp.dto.EmpIndexDto;
import com.icss.hr.emp.pojo.Emp;

/**
 * Ա��ȫ�ļ�����
 * @author Administrator
 *
 */
@Repository
public class EmpIndexDao {
	
	//Ա������Ŀ¼��ʹ����Դ�ļ��ļ�ֵ��
	@Value("#{prop.emp_index_path}")
	private String indexPath;
	
	// ���ķִ���
	public Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_47);
	
	/**
	 * ��������
	 */
	public void create(Document document) throws IOException {
		// ���������ķִ���
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		// ����Ŀ¼
		Directory directory = FSDirectory.open(new File(indexPath));

		IndexWriter indexWriter = new IndexWriter(directory, config);
		indexWriter.addDocument(document);
		indexWriter.commit();
		indexWriter.close();
		
		System.out.println("����������");
	}
	
	/**
	 * ����Term����ɾ����������������ݿ⣬����ID�Ϳ�����
	 * 
	 * Term����������С��λ������ĳ�� Field �е�һ���ؼ��ʣ��磺<title, lucene>
	 * 
	 * new Term( "title", "lucene" );
	 * 
	 * new Term( "id", "5" );
	 * 
	 * new Term( "id", UUID );
	 * 
	 * @param term
	 * @throws IOException
	 */
	public void delete(Term term) throws IOException {

		// ���������ķִ���
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		// ����Ŀ¼
		Directory directory = FSDirectory.open(new File(indexPath));

		IndexWriter indexWriter = new IndexWriter(directory, config);
		indexWriter.deleteDocuments(term);
		indexWriter.commit();
		indexWriter.close();
	}
	
	/**
	 * ����Term������������
	 * 
	 * @throws IOException
	 */
	public void update(Term term, Document document) throws IOException {
		// ���������ķִ���
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		// ����Ŀ¼
		Directory directory = FSDirectory.open(new File(indexPath));
		IndexWriter indexWriter = new IndexWriter(directory, config);
		indexWriter.updateDocument(term, document);
		indexWriter.commit();
		indexWriter.close();
	}
	
	/**
	 * ��������
	 * 
	 * @return
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	public EmpIndexDto search(Query query)
			throws IOException, InvalidTokenOffsetsException {

		// ���������ķִ���
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		
		// ����Ŀ¼
		Directory directory = FSDirectory.open(new File(indexPath));

		// IndexSearcher�����������õ�
		IndexReader ir = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(ir);

		// ���ˣ�Ч�ʱȽϵͣ���Ϊ��Ҫ���ж��α������ݣ�		
		//Filter filter = null;
				
		// ����(Ĭ�ϰ�����ض����򣬼�ƥ���Խ���Խ��ǰ�������ڰٶ�)
		Sort sort = new Sort();
		
		//Ҳ���԰����Զ����ֶ�����true��ʾ����false����
//		Sort sort = new Sort(new SortField("name", Type.STRING_VAL,false));

		// �õ�����������ǰ1000��
		TopDocs topDocs = indexSearcher.search(query, 1000, sort);
		
		// �ܼ�¼��
		int recordCount = topDocs.totalHits;

		// �ĵ�����
		List<Emp> recordList = new ArrayList<Emp>();

		// ============�����ͽ�ȡĳ���ֶε��ı�ժҪ����=============
		// ���û��Ƶ���β�ַ���
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
				"<font color=red>", "</font>");
		// �﷨������ʾ����
		Highlighter highlighter = new Highlighter(formatter, new QueryScorer(
				query));
		// 100���Ǳ�ʾժҪ������
		highlighter.setTextFragmenter(new SimpleFragmenter(100));
		// ===================================================

		/* ��õ�ǰҳ������ */
			
		for (int i = 0; i < recordCount; i++) {
			ScoreDoc scoreDoc = topDocs.scoreDocs[i];			
			int docSn = scoreDoc.doc; // �ĵ��ڲ����
			
			Document doc = indexSearcher.doc(docSn); // ���ݱ��ȡ����Ӧ���ĵ�

			// �õ�������ժҪ���֣�ʹ��Ա�����ҽ������ݣ�
			String contentStr = doc.get("empInfo");
			TokenStream tream = analyzer.tokenStream("empInfo",
					new StringReader(contentStr));
			String empInfo = highlighter
					.getBestFragment(tream, contentStr);

			// ������� �������ؼ��֣��᷵��null���ͽ�ȡǰ100���ַ�
			if (empInfo == null) {
				int minLen = contentStr.length() >= 100 ? 100 : contentStr
						.length();
				empInfo = contentStr.substring(0, minLen);
			}			
			
			//Ա�����
			Integer empId = Integer.parseInt(doc.get("empId"));
			//Ա������
			String empName = doc.get("empName");
			//�绰����
			String empPhone = doc.get("empPhone");
			
			//��װ��Emp
			Emp emp = new Emp();
			emp.setEmpId(empId);
			emp.setEmpName(empName);
			emp.setEmpPhone(empPhone);
			emp.setEmpInfo(empInfo);
			
			recordList.add(emp);
		}

		ir.close();
		
		//���ܼ�¼���ͼ��������װ��DTO������
		EmpIndexDto dto = new EmpIndexDto(recordCount,recordList);

		return dto; 
	}

}