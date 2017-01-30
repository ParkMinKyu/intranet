package com.min.intranet.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "main.do", method = RequestMethod.GET)
    public String main(Locale locale, Model model, HttpServletRequest req) {
	/*
	 * logger.info("Welcome main! The client locale is {}.", locale); String
	 * userSys = req.getHeader("user-agent"); boolean isM = false; String []
	 * checkM = {"iPhone", "iPod", "BlackBerry", "Android", "Windows CE",
	 * "LG", "MOT", "SAMSUNG", "SonyEricsson"}; for(String m : checkM){
	 * if(userSys.indexOf(m)!=-1){ isM = true; break; } } if(isM){ return
	 * "/mobile/main"; }else{ }
	 */
    	logger.info("Welcome main! The client locale is {}.", locale);
    	return "/home/main";
    }

}
