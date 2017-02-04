package com.min.intranet.service;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    public int getScheduleCount(
	    Map<String, Object> paramMap) throws Exception;

    public List<Map<String, String>> getScheduleArticles(
	    Map<String, Object> paramMap) throws Exception;

    public Map<String, String> getSchedule(Map<String, String> paramMap)
	    throws Exception;

    public int getScheduleMaxSeq() throws Exception;
    
    public int scheduleWrite(Map<String, String> paramMap) throws Exception;

    public int scheduleUpdate(Map<String, String> paramMap) throws Exception;

    public int scheduleDelete(Map<String, String> paramMap) throws Exception;
    
    public Map<String, String> getScheduleFile(Map<String, String> paramMap)throws Exception;
	
	public int scheduleFileWrite(Map<String, String> paramMap);

	public List<Map<String, String>> getScheduleFiles(String seq)throws Exception;

	public int scheduleDeleteFiles(Map<String, String> paramMap)throws Exception;

	public int deleteScheduleFiles(String name)throws Exception;
}
