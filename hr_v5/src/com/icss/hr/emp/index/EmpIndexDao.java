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
 * 员工全文检索类
 * @author Administrator
 *
 */
@Repository
public class EmpIndexDao {
	
	//员工索引目录，使用资源文件的键值对
	@Value("#{prop.emp_index_path}")
	private String indexPath;
	
	// 中文分词器
	public Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_47);
	
	/**
	 * 增加索引
	 */
	public void create(Document document) throws IOException {
		// 设置索引的分词器
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		// 索引目录
		Directory directory = FSDirectory.open(new File(indexPath));

		IndexWriter indexWriter = new IndexWriter(directory, config);
		indexWriter.addDocument(document);
		indexWriter.commit();
		indexWriter.close();
		
		System.out.println("索引被创建");
	}
	
	/**
	 * 根据Term条件删除索引，如果是数据库，根据ID就可以了
	 * 
	 * Term是搜索的最小单位，代表某个 Field 中的一个关键词，如：<title, lucene>
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

		// 设置索引的分词器
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		// 索引目录
		Directory directory = FSDirectory.open(new File(indexPath));

		IndexWriter indexWriter = new IndexWriter(directory, config);
		indexWriter.deleteDocuments(term);
		indexWriter.commit();
		indexWriter.close();
	}
	
	/**
	 * 根据Term条件更新索引
	 * 
	 * @throws IOException
	 */
	public void update(Term term, Document document) throws IOException {
		// 设置索引的分词器
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		// 索引目录
		Directory directory = FSDirectory.open(new File(indexPath));
		IndexWriter indexWriter = new IndexWriter(directory, config);
		indexWriter.updateDocument(term, document);
		indexWriter.commit();
		indexWriter.close();
	}
	
	/**
	 * 检索数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	public EmpIndexDto search(Query query)
			throws IOException, InvalidTokenOffsetsException {

		// 设置索引的分词器
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);
		
		// 索引目录
		Directory directory = FSDirectory.open(new File(indexPath));

		// IndexSearcher是用来搜索用的
		IndexReader ir = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(ir);

		// 过滤（效率比较低，因为需要进行二次遍历数据）		
		//Filter filter = null;
				
		// 排序(默认按照相关度排序，即匹配的越多就越靠前，类似于百度)
		Sort sort = new Sort();
		
		//也可以按照自定义字段排序，true表示升序，false降序
//		Sort sort = new Sort(new SortField("name", Type.STRING_VAL,false));

		// 得到满足条件的前1000行
		TopDocs topDocs = indexSearcher.search(query, 1000, sort);
		
		// 总记录数
		int recordCount = topDocs.totalHits;

		// 文档集合
		List<Emp> recordList = new ArrayList<Emp>();

		// ============高亮和截取某个字段的文本摘要设置=============
		// 设置环绕的首尾字符串
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
				"<font color=red>", "</font>");
		// 语法高亮显示设置
		Highlighter highlighter = new Highlighter(formatter, new QueryScorer(
				query));
		// 100是是表示摘要的字数
		highlighter.setTextFragmenter(new SimpleFragmenter(100));
		// ===================================================

		/* 获得当前页的数据 */
			
		for (int i = 0; i < recordCount; i++) {
			ScoreDoc scoreDoc = topDocs.scoreDocs[i];			
			int docSn = scoreDoc.doc; // 文档内部编号
			
			Document doc = indexSearcher.doc(docSn); // 根据编号取出相应的文档

			// 得到高亮及摘要文字（使用员工自我介绍内容）
			String contentStr = doc.get("empInfo");
			TokenStream tream = analyzer.tokenStream("empInfo",
					new StringReader(contentStr));
			String empInfo = highlighter
					.getBestFragment(tream, contentStr);

			// 如果内容 不包含关键字，会返回null，就截取前100个字符
			if (empInfo == null) {
				int minLen = contentStr.length() >= 100 ? 100 : contentStr
						.length();
				empInfo = contentStr.substring(0, minLen);
			}			
			
			//员工编号
			Integer empId = Integer.parseInt(doc.get("empId"));
			//员工姓名
			String empName = doc.get("empName");
			//电话号码
			String empPhone = doc.get("empPhone");
			
			//封装到Emp
			Emp emp = new Emp();
			emp.setEmpId(empId);
			emp.setEmpName(empName);
			emp.setEmpPhone(empPhone);
			emp.setEmpInfo(empInfo);
			
			recordList.add(emp);
		}

		ir.close();
		
		//把总记录数和检索结果封装到DTO对象中
		EmpIndexDto dto = new EmpIndexDto(recordCount,recordList);

		return dto; 
	}

}