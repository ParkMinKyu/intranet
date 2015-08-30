package com.min.intranet.service;

import java.util.Map;

public interface UserService {
	 public Map<String, Object> getUser(Map<String, String> paramMap)throws Exception;
	 
	 public int passwdChange(Map<String, Object> paramMap)throws Exception;
	 
	 public int addEmployee(Map<String, String> paramMap) throws Exception;
}
