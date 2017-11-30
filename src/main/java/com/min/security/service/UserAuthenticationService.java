package com.min.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.min.intranet.core.DefaultEncryptor;

public class UserAuthenticationService implements UserDetailsService {

	 @Value("${niee.adminEmail}")
	 private String adminEmail;
	
	@Resource(name = "defaultEncryptor")
	private DefaultEncryptor encryptor;
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);
	private SqlSessionTemplate sqlSession;
	
	public UserAuthenticationService() {
		// TODO Auto-generated constructor stub
	}

	public UserAuthenticationService(SqlSessionTemplate sqlSession) {
		// TODO Auto-generated constructor stub
		this.sqlSession = sqlSession;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Map<String, Object> user = sqlSession.selectOne("employee.getUser",encryptor.base64Encoding(username));
		if(user == null ) throw new UsernameNotFoundException(username);
		String email = encryptor.base64Decoding(user.get("username").toString());
		logger.info(user.toString());
		List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
		if(email.equals(adminEmail))
			gas.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		else if(email.equals("guest"))
			gas.add(new SimpleGrantedAuthority("ROLE_GUEST"));
		else
			gas.add(new SimpleGrantedAuthority("ROLE_USER"));
		//return new UserDetailsVO(user.get("username").toString(), user.get("password").toString(), (Integer)user.get("enabled") == 1, true, true, true, gas,user.get("username").toString());
		return new User(email, user.get("password").toString(), Integer.parseInt(user.get("enabled").toString()) == 1, true, true, true, gas);
	}

}
