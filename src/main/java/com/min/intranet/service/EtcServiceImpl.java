package com.min.intranet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service(value = "etcService")
public class EtcServiceImpl extends SqlSessionDaoSupport implements EtcService {

	@Override
	public List<Map<String, String>> getEtcList(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, String>> etcs = getSqlSession().selectList("etc.getList", paramMap);
		
		for(Map<String, String> map : etcs){
			String title = map.get("title");
			String contents = map.get("contents");
			title = title.replaceAll("<script>","&lt;script&gt;");
			title = title.replaceAll("</script>","&lt;/script&gt;");
			contents = contents.replaceAll("<script>","&lt;script&gt;");
			contents = contents.replaceAll("</script>","&lt;/script&gt;");
			map.put("title",title);
			map.put("contents",contents);
		}
		
		return etcs.size()==0?new ArrayList<Map<String,String>>():etcs;
	}

	@Override
	public List<Map<String, String>> getChart(Map<String, String> paramMap)
		throws Exception {
	    // TODO Auto-generated method stub
	    List<Map<String, String>> charts = getSqlSession().selectList("etc.getChart", paramMap);
		
	    return charts.size()==0?new ArrayList<Map<String,String>>():charts;
	}

}