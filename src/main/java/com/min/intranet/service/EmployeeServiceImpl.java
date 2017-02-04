package com.min.intranet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.intranet.core.DefaultEncryptor;

@Service(value = "employeeService")
@Transactional
public class EmployeeServiceImpl extends SqlSessionDaoSupport implements EmployeeService {

	@Resource(name = "defaultEncryptor")
	private DefaultEncryptor encryptor;
	
	@Override
	public List<Map<String, String>> getCmpanyList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, String>> company = getSqlSession().selectList("company.getList", paramMap);
		return company.size()==0?new ArrayList<Map<String,String>>():company;
	}
	
	@Override
	public List<Map<String, String>> getDepartmentList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, String>> department = getSqlSession().selectList("department.getList", paramMap);
		return department.size()==0?new ArrayList<Map<String,String>>():department;
	}

	@Override
	public List<Map<String, String>> getEmployeeList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		List<Map<String, String>> employee = getSqlSession().selectList("employee.getList", paramMap);
		for(Map<String, String> map : employee){
			String email =	map.get("email");
			String phone = map.get("phone");
			map.put("email", encryptor.base64Decoding(email));
			map.put("phone", encryptor.base64Decoding(phone));
		}
		return employee.size()==0?new ArrayList<Map<String,String>>():employee;
	}

	@Override
	public List<Map<String, String>> getUserArticles(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String email =	paramMap.get("email").toString();
		paramMap.put("email", encryptor.base64Encoding(email));
		List<Map<String, String>> userArticle = getSqlSession().selectList("userArticle.getList", paramMap);
		
		return userArticle.size()==0?new ArrayList<Map<String,String>>():userArticle;
	}
	
	@Override
	public int getUserArticlesCount(Map<String, Object> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("userArticle.getCount", paramMap);
	}
	
	@Override
	public Map<String, String> getArticle(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = getSqlSession().selectOne("userArticle.getArticle", paramMap);
		String writer = resultMap.get("writer");
		String receiver = resultMap.get("receiver");
		String title = resultMap.get("title");
		String contents = resultMap.get("contents");
		title = title.replaceAll("<script>","&lt;script&gt;");
		title = title.replaceAll("</script>","&lt;/script&gt;");
		contents = contents.replaceAll("<script>","&lt;script&gt;");
		contents = contents.replaceAll("</script>","&lt;/script&gt;");
		resultMap.put("title",title);
		resultMap.put("contents",contents);
		resultMap.put("writer",encryptor.base64Decoding(writer));
		resultMap.put("receiver",encryptor.base64Decoding(receiver));
		return resultMap;
	}

	@Override
	public int userArticleWrite(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		paramMap.put("writer", encryptor.base64Encoding(paramMap.get("writer")));
		paramMap.put("receiver", encryptor.base64Encoding(paramMap.get("receiver")));
		return getSqlSession().insert("userArticle.insert", paramMap);
	}
	
	@Override
	public void updateArticle(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		getSqlSession().update("userArticle.update", paramMap);
	}

	@Override
	public int getMaxSeq() throws Exception {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("userArticle.getMaxSeq");
	}

	@Override
	public int userFileWrite(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getSqlSession().insert("userArticle.insertFile", paramMap);
	}

	@Override
	public List<Map<String, String>> getUserFiles(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, String>> files = getSqlSession().selectList("userArticle.getUserFiles", paramMap);
		
	    return files.size()==0?new ArrayList<Map<String,String>>():files;
	}

	@Override
	public Map<String, String> getUserFile(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = getSqlSession().selectOne("userArticle.getFile", paramMap);
	    return map;
	}
}