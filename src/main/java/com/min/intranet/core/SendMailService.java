package com.min.intranet.core;

import java.util.List;
import java.util.Map;

import javax.activation.DataSource;

public interface SendMailService {
	public void sendMail(Map<String,String> paramMap);
	
	public void sendMail(Map<String, String> paramMap, List<DataSource> dslist,
			List<String> rnlist);
}
