package com.min.intranet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.intranet.service.EtcService;

@Controller
@RequestMapping("/home")
public class EtcController {

	private static final Logger logger = LoggerFactory.getLogger(EtcController.class);

	@Autowired
	private EtcService etcService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "getEtc.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getEtc(Locale locale, Model model, @RequestParam("syear") String syear,
			@RequestParam("smonth") String smonth, @RequestParam("eyear") String eyear,
			@RequestParam("emonth") String emonth) throws Exception {
		logger.info("Welcome getEtc! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sDay", syear + "/" + smonth + "/01");
		paramMap.put("eDay", eyear + "/" + emonth + "/01");
		List<Map<String, String>> etcMap = etcService.getEtcList(paramMap);

		return etcMap;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "getChart.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getChart(Locale locale, Model model) throws Exception {
		logger.info("Welcome getUserFiles! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		List<Map<String, String>> chartList = etcService.getChart(paramMap);

		return chartList;
	}
}
