package com.min.intranet.service;

import java.util.List;
import java.util.Map;

public interface EtcService {

	List<Map<String, String>> getEtcList(Map<String, String> paramMap) throws Exception;

	List<Map<String, String>> getChart(Map<String, String> paramMap) throws Exception;

}