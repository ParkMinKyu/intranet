package com.min.intranet.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.min.intranet.service.ScheduleService;

@Controller
@RequestMapping("/common")
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Resource(name = "homeService")
	private ScheduleService homeService;
	
	@RequestMapping(value = "error.do", method = RequestMethod.GET)
	public String common(Model model, @RequestParam("error")String code){
		logger.info(code);
		model.addAttribute("code", code);
		return "/common/error";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "getSchedule.do", method = RequestMethod.GET)
	public String getSchedule(Locale locale, Model model, @RequestParam("seq")String seq,HttpServletRequest req) throws Exception {
		logger.info("Welcome getSchedule! The client locale is {}.", locale);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);
		Map<String, String> schedule = homeService.getSchedule(paramMap);
		model.addAttribute("schedule", schedule);
		return "common/scheduleView";
	}
}
