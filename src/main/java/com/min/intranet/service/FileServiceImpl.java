package com.min.intranet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.intranet.core.DefaultEncryptor;

@Service(value = "fileService")
@Transactional
public class FileServiceImpl extends SqlSessionDaoSupport implements FileService {

	@Resource(name = "defaultEncryptor")
	private DefaultEncryptor encryptor;
	
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

}