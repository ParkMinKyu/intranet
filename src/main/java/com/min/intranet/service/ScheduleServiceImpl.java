package com.min.intranet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.intranet.core.DefaultEncryptor;

@Service(value = "scheduleService")
@Transactional
public class ScheduleServiceImpl extends SqlSessionDaoSupport implements ScheduleService {

	@Resource(name = "defaultEncryptor")
	private DefaultEncryptor encryptor;
	
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
	public Map<String, String> getScheduleFile(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = getSqlSession().selectOne("scheduleArticle.getFile", paramMap);
		return map;
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