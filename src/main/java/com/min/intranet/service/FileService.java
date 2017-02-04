package com.min.intranet.service;

import java.util.List;
import java.util.Map;

public interface FileService {

	List<Map<String, String>> getFileList(Map<String, String> paramMap) throws Exception;

	int fileWrite(Map<String, String> paramMap) throws Exception;

	Map<String, String> getFile(Map<String, String> paramMap) throws Exception;

	int deleteFile(Map<String, String> paramMap) throws Exception;

}