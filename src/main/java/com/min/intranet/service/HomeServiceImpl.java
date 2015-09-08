package com.min.intranet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.intranet.core.DefaultEncryptor;

@Service(value = "homeService")
@Transactional
public class HomeServiceImpl extends SqlSessionDaoSupport implements HomeService {

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
	public int getScheduleMaxSeq() throws Exception{
		return getSqlSession().selectOne("scheduleArticle.getMaxSeq");
	}
	
	@Override
	public int scheduleFileWrite(Map<String, String> paramMap){
		return getSqlSession().insert("scheduleArticle.insertFile", paramMap);
	}
	
	@Override
	public List<Map<String, String>> getScheduleFiles(String seq) throws Exception {
		// TODO Auto-generated method stub
		
		List<Map<String, String>> scheduleFiles = getSqlSession().selectList("scheduleArticle.getScheduleFiles", seq);
		
		return scheduleFiles.size()==0?new ArrayList<Map<String,String>>():scheduleFiles;
	}
	
	@Override
	public List<Map<String, String>> getScheduleArticles(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub

		List<Map<String, String>> scheduleArticle = getSqlSession().selectList("scheduleArticle.getList", paramMap);
		
		for(Map<String, String> map : scheduleArticle){
			String title = map.get("title");
			title = title.replaceAll("<script>","&lt;script&gt;");
			title = title.replaceAll("</script>","&lt;/script&gt;");
			map.put("title",title);
		}
		
		return scheduleArticle.size()==0?new ArrayList<Map<String,String>>():scheduleArticle;
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
	public int scheduleWrite(Map<String, String> paramMap)throws Exception {
		// TODO Auto-generated method stub
		paramMap.put("writer", encryptor.base64Encoding(paramMap.get("writer")));
		return getSqlSession().insert("scheduleArticle.insert", paramMap);
	}

	@Override
	public Map<String, String> getSchedule(Map<String, String> paramMap)throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = getSqlSession().selectOne("scheduleArticle.getSchedule", paramMap);
		if(resultMap != null){
			String email = resultMap.get("email");
			String title = resultMap.get("title");
			String contents = resultMap.get("contents");
			title = title.replaceAll("<script>","&lt;script&gt;");
			title = title.replaceAll("</script>","&lt;/script&gt;");
			contents = contents.replaceAll("<script>","&lt;script&gt;");
			contents = contents.replaceAll("</script>","&lt;/script&gt;");
			resultMap.put("title",title);
			resultMap.put("contents",contents);
			resultMap.put("email",encryptor.base64Decoding(email));
		}
		return resultMap;
	}

	@Override
	public int scheduleDelete(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String writer = paramMap.get("writer");
		paramMap.put("writer",encryptor.base64Encoding(writer));
		return getSqlSession().delete("scheduleArticle.delete", paramMap);
	}

	@Override
	public int scheduleUpdate(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String writer = paramMap.get("writer");
		paramMap.put("writer",encryptor.base64Encoding(writer));
		return getSqlSession().update("scheduleArticle.update",paramMap);
	}

	@Override
	public int getScheduleCount(Map<String, Object> paramMap)
		throws Exception {
	    // TODO Auto-generated method stub
	    return getSqlSession().selectOne("scheduleArticle.getCount", paramMap);
	}

	@Override
	public List<Map<String, String>> getEtcList(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, String>> etcs = getSqlSession().selectList("etc.getList", paramMap);
		
		for(Map<String, String> map : etcs){
			String title = map.get("title");
			String contents = map.get("contents");
			title = title.replaceAll("<script>","&lt;script&gt;");
			title = title.replaceAll("</script>","&lt;/script&gt;");
			contents = contents.replaceAll("<script>","&lt;script&gt;");
			contents = contents.replaceAll("</script>","&lt;/script&gt;");
			map.put("title",title);
			map.put("contents",contents);
		}
		
		return etcs.size()==0?new ArrayList<Map<String,String>>():etcs;
	}

	@Override
	public int fileWrite(Map<String, String> paramMap) throws Exception {
	    // TODO Auto-generated method stub
	    paramMap.put("email", encryptor.base64Encoding(paramMap.get("email")));
	    return getSqlSession().insert("file.insert", paramMap);
	}

	@Override
	public List<Map<String, String>> getFileList(
		Map<String, String> paramMap) throws Exception {
	    // TODO Auto-generated method stub
	    List<Map<String, String>> files = getSqlSession().selectList("file.getList", paramMap);
		
	    for(Map<String, String> map : files){
		String email = map.get("email");
		map.put("email",encryptor.base64Decoding(email));
	    }
		
	    return files.size()==0?new ArrayList<Map<String,String>>():files;
	}

	@Override
	public Map<String, String> getFile(Map<String, String> paramMap)
		throws Exception {
	    // TODO Auto-generated method stub
		Map<String, String> map = getSqlSession().selectOne("file.getFile", paramMap);
		map.put("email", encryptor.base64Decoding(map.get("email")));
	    return map;
	}

	@Override
	public int deleteFile(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return getSqlSession().delete("file.delete", paramMap);
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

	@Override
	public Map<String, String> getScheduleFile(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = getSqlSession().selectOne("scheduleArticle.getFile", paramMap);
		return map;
	}
	
	@Override
	public List<Map<String, String>> getChart(Map<String, String> paramMap)
		throws Exception {
	    // TODO Auto-generated method stub
	    List<Map<String, String>> charts = getSqlSession().selectList("etc.getChart", paramMap);
		
	    return charts.size()==0?new ArrayList<Map<String,String>>():charts;
	}

	@Override
	public int scheduleDeleteFiles(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		return getSqlSession().delete("scheduleArticle.deleteFiles", paramMap);
	}

	@Override
	public int deleteScheduleFiles(String name) throws Exception {
	    // TODO Auto-generated method stub
	    return getSqlSession().delete("scheduleArticle.deleteScheduleFiles", name);
	}

}