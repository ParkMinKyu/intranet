package com.min.intranet.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.min.intranet.core.DefaultEncryptor;

@Service(value = "userService")
@Transactional
public class UserServiceImpl extends SqlSessionDaoSupport implements UserService {

	@Resource(name = "defaultEncryptor")
	private DefaultEncryptor encryptor;
	
	@Resource(name = "passwordEncoder")
	private ShaPasswordEncoder passwordEncoderService;
	
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
			resultMap.put("passwd",passwordEncoderService.encodePassword(passwd, null));
		}
		return resultMap;
	}

	@Override
	public int passwdChange(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String email = (String) paramMap.get("email");
		String passwd = (String) paramMap.get("passwd");
		paramMap.put("email", encryptor.base64Encoding(email));
		paramMap.put("passwd", passwordEncoderService.encodePassword(passwd, null));
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
			paramMap.put("email", encryptor.base64Encoding(email));
			paramMap.put("phone", encryptor.base64Encoding(phone));
			if(paramMap.containsKey("passwd")){
			    paramMap.put("passwd", passwordEncoderService.encodePassword(paramMap.get("passwd"), null));
			}else{
			    paramMap.put("passwd", passwordEncoderService.encodePassword("123456", null));
			}
			return getSqlSession().insert("employee.addEmployee", paramMap);
		}
	}
}