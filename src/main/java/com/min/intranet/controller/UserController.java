package com.min.intranet.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.intranet.core.CommonUtil;
import com.min.intranet.core.SendMailService;
import com.min.intranet.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @Value("${niee.adminEmail}")
    private String adminEmail;
    
    @Value("${niee.adminPass}")
    private String adminPass;
    
    @Autowired
    private UserService userService;

    @Autowired
    private SendMailService sendMailService;
    
    @RequestMapping(value = "loginPage.do", method = RequestMethod.GET)
    public String loginPage(Locale locale, Model model) {
	logger.info("Welcome loginPage! The client locale is {}.", locale);
	return "/user/loginPage";
    }

    @RequestMapping(value = "session.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> session(Locale locale, Model model) {
	logger.info("Welcome session! The client locale is {}.", locale);
	Map<String, String> map = new HashMap<String, String>();
	map.put("locale", locale.toString());
	return map;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public String logout(Locale locale, Model model, HttpServletRequest req) {
	logger.info("Welcome logout! The client locale is {}.", locale);
	HttpSession session = req.getSession();
	session.invalidate();
	return "redirect:/user/loginPage.do";
    }

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("email") String email,
	    @RequestParam("passwd") String passwd) throws Exception {
	logger.info("Welcome login! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();
	Map<String, Object> userMap = new HashMap<String, Object>();
	HttpSession session = req.getSession();

	if(adminEmail.equals(email) && adminPass.equals(passwd)){
	    paramMap.put("email", adminEmail);
	    if(userService.getUser(paramMap).isEmpty()){
		paramMap.put("email", adminEmail);
		paramMap.put("name", "admin");
		paramMap.put("passwd", adminPass);
		paramMap.put("phone", "000-0000-0000");
		userService.addEmployee(paramMap);
	    }
	    session.setAttribute(CommonUtil.SESSION_USER,adminEmail);
	    session.setAttribute("isAdmin",true);
	    userMap.put("isLogin", true);
	    return userMap;
	}
	
	if ("".equals(email)) {
	    userMap.put("isLogin", false);
	    userMap.put("msg", "메일주소를 입력하세요.");
	} else {
	    if (email.indexOf("@") == -1) {
		email += "@asianaidt.com";
	    }
	    paramMap.put("email", email);
	    userMap = userService.getUser(paramMap);
	    if (passwd.equals(userMap.get("passwd"))) {
		session.setAttribute(CommonUtil.SESSION_USER,
			userMap.get("email"));
		userMap.put("isLogin", true);
	    } else {
		userMap.put("isLogin", false);
		userMap.put("msg", "입력하신 정보가 일치하지 않습니다.");
	    }
	}
	return userMap;
    }

    @RequestMapping(value = "changePasswd.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePasswd(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("oldpass") String oldpass,
	    @RequestParam("newpass") String newpass) throws Exception {
	logger.info("Welcome changePasswd! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();
	Map<String, Object> userMap = new HashMap<String, Object>();
	HttpSession session = req.getSession();
	String email = (String) session.getAttribute(CommonUtil.SESSION_USER);

	paramMap.put("email", email);
	userMap = userService.getUser(paramMap);
	if (oldpass.equals(userMap.get("passwd"))) {
	    userMap.put("success", true);
	    String passwd = newpass;
	    userMap.put("passwd", passwd);
	    int cnt = userService.passwdChange(userMap);
	    if (cnt > 0) {
		paramMap.put("title", "비밀번호 변경 알림");
		paramMap.put("name", (String) userMap.get("name"));
		paramMap.put("email", email);
		paramMap.put("from", "niee@urielsoft.co.kr");
		paramMap.put("to", email);
		paramMap.put("passwd", passwd);
		paramMap.put("templateName", "findPasswd.ftl");
		sendMailService.sendMail(paramMap);
		userMap.put("success", true);
		userMap.put("msg", "비밀번호가 변경되었습니다.");
	    } else {
		userMap.put("success", false);
		userMap.put("msg", "잠시 후 다시 시도하세요.");
	    }
	} else {
	    userMap.put("success", false);
	    userMap.put("msg", "입력하신 정보가 일치하지 않습니다.");
	}
	return userMap;
    }

    @RequestMapping(value = "findPasswd.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findPasswd(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("email") String email)
	    throws Exception {
	logger.info("Welcome findPasswd! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();
	Map<String, Object> userMap = new HashMap<String, Object>();

	if ("".equals(email)) {
	    userMap.put("success", false);
	    userMap.put("msg", "메일주소를 입력하세요.");
	} else {
	    paramMap.put("email", email);
	    userMap = userService.getUser(paramMap);
	    if (userMap.isEmpty()) {
		userMap.put("success", false);
		userMap.put("msg", "입력하신 정보가 일치하지 않습니다.");
	    } else {
		String passwd = UUID.randomUUID().toString();
		userMap.put("passwd", passwd);
		int cnt = userService.passwdChange(userMap);
		if (cnt > 0) {
		    paramMap.put("title", "비밀번호 변경 알림");
		    paramMap.put("name", (String) userMap.get("name"));
		    paramMap.put("email", email);
		    paramMap.put("from", "niee@urielsoft.co.kr");
		    paramMap.put("to", email);
		    paramMap.put("passwd", passwd);
		    paramMap.put("templateName", "findPasswd.ftl");
		    sendMailService.sendMail(paramMap);
		    userMap.put("success", true);
		    userMap.put("msg", "가입하신 메일주소로 \n새로운 비밀번호가 발급되었습니다.");
		} else {
		    userMap.put("success", false);
		    userMap.put("msg", "잠시 후 다시 시도하세요.");
		}
	    }
	}
	return userMap;
    }

    @RequestMapping(value = "addEmployee.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addEmployee(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("email") String email,
	    @RequestParam("name") String name,
	    @RequestParam("phone") String phone) throws Exception {
	logger.info("Welcome addEmployee! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("email", email);
	paramMap.put("name", name);
	paramMap.put("phone", phone);
	Map<String, Object> resultMap = new HashMap<String, Object>();
	if (req.getSession().getAttribute(CommonUtil.SESSION_USER)
		.equals(adminEmail)) {
	    int cnt = userService.addEmployee(paramMap);
	    if (cnt == 0) {
		resultMap.put("msg", "사용자 추가 실패.");
	    } else {
		resultMap.put("msg", "사용자가 추가 되었습니다.");
	    }
	} else {
	    resultMap.put("msg", "권한이 없습니다.");
	}
	return resultMap;
    }
}
