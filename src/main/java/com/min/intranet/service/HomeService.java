package com.min.intranet.service;

import java.util.List;
import java.util.Map;

public interface HomeService {

    public List<Map<String, String>> getCmpanyList(Map<String, String> paramMap)
	    throws Exception;

    public List<Map<String, String>> getDepartmentList(
	    Map<String, String> paramMap) throws Exception;

    public List<Map<String, String>> getEmployeeList(
	    Map<String, String> paramMap) throws Exception;

    public List<Map<String, String>> getUserArticles(
	    Map<String, Object> paramMap) throws Exception;

    public int getScheduleCount(
	    Map<String, Object> paramMap) throws Exception;

    public List<Map<String, String>> getScheduleArticles(
	    Map<String, Object> paramMap) throws Exception;

    public List<Map<String, String>> getEtcList(
    		Map<String, String> paramMap) throws Exception;

    public Map<String, String> getArticle(Map<String, String> paramMap)
	    throws Exception;

    public Map<String, String> getSchedule(Map<String, String> paramMap)
	    throws Exception;

    public int getScheduleMaxSeq() throws Exception;
    
    public int scheduleWrite(Map<String, String> paramMap) throws Exception;

    public int scheduleUpdate(Map<String, String> paramMap) throws Exception;

    public int scheduleDelete(Map<String, String> paramMap) throws Exception;
    
    public List<Map<String, String>> getFileList(
		Map<String, String> paramMap) throws Exception;
    
    public int fileWrite(Map<String, String> paramMap) throws Exception;

    public Map<String, String> getFile(Map<String, String> paramMap)throws Exception;

	public int deleteFile(Map<String, String> paramMap)throws Exception;

	public int getUserArticlesCount(Map<String, Object> paramMap)throws Exception;

	public int userArticleWrite(Map<String, String> paramMap)throws Exception;

	public int getMaxSeq()throws Exception;

	public void updateArticle(Map<String, String> paramMap)throws Exception;

	public int userFileWrite(Map<String, String> paramMap)throws Exception;

	public List<Map<String, String>> getUserFiles(Map<String, String> paramMap)throws Exception;

	public Map<String, String> getUserFile(Map<String, String> paramMap)throws Exception;

	public Map<String, String> getScheduleFile(Map<String, String> paramMap)throws Exception;
	
	public List<Map<String, String>> getChart(Map<String, String> paramMap)throws Exception;

	public int scheduleFileWrite(Map<String, String> paramMap);

	public List<Map<String, String>> getScheduleFiles(String seq)throws Exception;

	public int scheduleDeleteFiles(Map<String, String> paramMap)throws Exception;

	public int deleteScheduleFiles(String name)throws Exception;
}
