package com.min.intranet.service;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

	List<Map<String, String>> getCmpanyList(Map<String, String> paramMap) throws Exception;

	List<Map<String, String>> getDepartmentList(Map<String, String> paramMap) throws Exception;

	List<Map<String, String>> getEmployeeList(Map<String, String> paramMap) throws Exception;

	List<Map<String, String>> getUserArticles(Map<String, Object> paramMap) throws Exception;

	Map<String, String> getArticle(Map<String, String> paramMap) throws Exception;

	int getUserArticlesCount(Map<String, Object> paramMap) throws Exception;

	int userArticleWrite(Map<String, String> paramMap) throws Exception;

	int getMaxSeq() throws Exception;

	void updateArticle(Map<String, String> paramMap) throws Exception;

	int userFileWrite(Map<String, String> paramMap) throws Exception;

	List<Map<String, String>> getUserFiles(Map<String, String> paramMap) throws Exception;

	public Map<String, String> getUserFile(Map<String, String> paramMap)throws Exception;

}