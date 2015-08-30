package com.min.intranet.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.min.intranet.core.DefaultEncryptor;

@Service(value = "userService")
@Transactional
public class UserServiceImpl extends SqlSessionDaoSupport implements UserService {

	@Resource(name = "defaultEncryptor")
	private DefaultEncryptor encryptor;
	
	@Override
	public Map<String, Object> getUser(Map<String, String> paramMap)throws Exception {
		// TODO Auto-generated method stub
		String email = paramMap.get("email");
		paramMap.put("email",encryptor.base64Encoding(email));
		Map<String, Object> resultMap = getSqlSession().selectOne("employee.getUser", paramMap);
		if(resultMap == null){
			resultMap = new HashMap<String, Object>();
		}else{
			String dbemail = (String) resultMap.get("email");
			String passwd = (String) resultMap.get("passwd");
			resultMap.put("email",encryptor.base64Decoding(dbemail));
			resultMap.put("passwd",encryptor.decode(passwd));
		}
		return resultMap;
	}

	@Override
	public int passwdChange(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String email = (String) paramMap.get("email");
		String passwd = (String) paramMap.get("passwd");
		paramMap.put("email", encryptor.base64Encoding(email));
		paramMap.put("passwd", encryptor.encode(passwd));
		return getSqlSession().update("employee.passwdChange",paramMap);
	}

	@Override
	public int addEmployee(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String email = paramMap.get("email");
		String name = paramMap.get("name");
		String phone = paramMap.get("phone");
		if(StringUtils.isEmpty(email) || StringUtils.isEmpty(name) || StringUtils.isEmpty(phone)){
			return 0;
		}else{
			if(email.indexOf("@")==-1){
				email += "@naver.com";
			}
			paramMap.put("email", encryptor.base64Encoding(email));
			paramMap.put("phone", encryptor.base64Encoding(phone));
			if(paramMap.containsKey("passwd")){
			    paramMap.put("passwd", encryptor.encode(paramMap.get("passwd")));
			}else{
			    paramMap.put("passwd", encryptor.encode("123456"));
			}
			return getSqlSession().insert("employee.addEmployee", paramMap);
		}
	}
}