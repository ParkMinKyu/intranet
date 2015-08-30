package com.min.intranet.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import org.springframework.web.servlet.ModelAndView;

import com.min.intranet.core.CommonUtil;
import com.min.intranet.core.SendMailService;
import com.min.intranet.service.HomeService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Value("${niee.fileDir}")
    private String fileDir;
    
    @Value("${niee.imageDir}")
    private String imageDir;
    
    @Autowired
    private HomeService homeService;

    @Autowired
    private SendMailService sendMailService;

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
	return "/home/main";
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "company.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> company(Locale locale, Model model,
	    @RequestParam("seq") String seq) throws Exception {
	logger.info("Welcome company! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);

	List<Map<String, String>> company = homeService.getCmpanyList(paramMap);

	return company;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "department.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> department(Locale locale, Model model,
	    @RequestParam("seq") String seq) throws Exception {
	logger.info("Welcome department! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);

	List<Map<String, String>> department = homeService
		.getDepartmentList(paramMap);

	return department;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "employee.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> user(Locale locale, Model model,
	    @RequestParam("seq") String seq) throws Exception {
	logger.info("Welcome employee! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);
	List<Map<String, String>> employee = homeService
		.getEmployeeList(paramMap);

	return employee;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "userArticle.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> userArticle(Locale locale, Model model,
	    HttpServletRequest req) throws Exception {
	logger.info("Welcome userArticle! The client locale is {}.", locale);

	String page = req.getParameter("page");
	String today = req.getParameter("today");
	String sType = req.getParameter("sType");
	String sText = req.getParameter("sText");
	String email = req.getParameter("email");

	int startRow = (Integer.parseInt(page == null ? "1" : page) - 1) * 15;
	if (startRow < 0) {
	    startRow = 0;
	}
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put("startRow", startRow);
	paramMap.put("today", today);
	paramMap.put("sType", sType);
	paramMap.put("sText", sText);
	paramMap.put("email", email);

	List<Map<String, String>> userArticle = homeService
		.getUserArticles(paramMap);

	Map<String, Object> resultMap = new HashMap<String, Object>();

	resultMap.put("count", homeService.getUserArticlesCount(paramMap));
	resultMap.put("page", page);
	resultMap.put("articles", userArticle);

	return resultMap;
    }

    @RequestMapping(value = "userFileDelete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userFileDelete(Locale locale, Model model,
	    @RequestParam("name") String name) throws Exception {
	logger.info("Welcome userFileDelete! The client locale is {}.", locale);
	Map<String, Object> resultMap = new HashMap<String, Object>();
	File file = new File(fileDir + name);
	if (file.isFile()) {
	    resultMap.put("isDel", file.delete());
	}
	return resultMap;
    }

    @RequestMapping(value = "scheduleFileDelete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> scheduleFileDelete(Locale locale, Model model,
	    @RequestParam("name") String name) throws Exception {
	logger.info("Welcome userFileDelete! The client locale is {}.", locale);
	Map<String, Object> resultMap = new HashMap<String, Object>();
	File file = new File(fileDir + name);
	if (file.isFile()) {
	    resultMap.put("isDel", file.delete());
	    resultMap.put("dbDel", homeService.deleteScheduleFiles(name));
	}
	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "sendUserMail.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sendUserMail(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("title") String title,
	    @RequestParam("contents") String contents,
	    @RequestParam("email") String email,
	    @RequestParam("isSend") boolean isSend,
	    @RequestParam("realnames") String realnames,
	    @RequestParam("subnames") String subnames,
	    @RequestParam("ccs") String ccs) throws Exception {
	logger.info("Welcome sendUserMail! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();
	Map<String, Object> resultMap = new HashMap<String, Object>();
	String writer = (String) req.getSession().getAttribute(
		CommonUtil.SESSION_USER);
	StringTokenizer realname = new StringTokenizer(realnames, ",");
	StringTokenizer subname = new StringTokenizer(subnames, ",");
	StringTokenizer cclist = new StringTokenizer(ccs, ",");

	int seq = homeService.getMaxSeq();
	paramMap.put("seq", "" + (seq + 1));
	paramMap.put("writer", writer);
	paramMap.put("receiver", email);
	paramMap.put("title", title);
	paramMap.put("contents", contents);
	resultMap.put("resultCnt", homeService.userArticleWrite(paramMap));
	resultMap.put("msg", "등록 되었습니다.");
	while (realname.hasMoreElements() && subname.hasMoreElements()) {
	    Map<String, String> fileMap = new HashMap<String, String>();
	    String rname = realname.nextToken();
	    String sname = subname.nextToken();
	    fileMap.put("userseq", "" + (seq + 1));
	    fileMap.put("realname", rname);
	    fileMap.put("subname", sname);
	    int cnt = homeService.userFileWrite(fileMap);
	    if (cnt == 0)
		resultMap.put("msg", "파일 등록 실패");
	}

	while (cclist.hasMoreElements()) {
	    String cc = cclist.nextToken();
	    seq = homeService.getMaxSeq();
	    paramMap.put("seq", "" + (seq + 1));
	    paramMap.put("writer", writer);
	    paramMap.put("receiver", cc);
	    paramMap.put("title", title);
	    paramMap.put("contents", contents);
	    resultMap.put("resultCnt", homeService.userArticleWrite(paramMap));
	    resultMap.put("msg", "등록 되었습니다.");
	    realname = new StringTokenizer(realnames, ",");
	    subname = new StringTokenizer(subnames, ",");
	    while (realname.hasMoreElements() && subname.hasMoreElements()) {
		Map<String, String> fileMap = new HashMap<String, String>();
		String rname = realname.nextToken();
		String sname = subname.nextToken();
		fileMap.put("userseq", "" + (seq + 1));
		fileMap.put("realname", rname);
		fileMap.put("subname", sname);
		int cnt = homeService.userFileWrite(fileMap);
		if (cnt == 0)
		    resultMap.put("msg", "파일 등록 실패");
	    }
	}

	if (isSend) {
	    Map<String, String> mailMap = new HashMap<String, String>();
	    List<DataSource> dslist = new ArrayList<DataSource>();
	    List<String> rnlist = new ArrayList<String>();
	    realname = new StringTokenizer(realnames, ",");
	    subname = new StringTokenizer(subnames, ",");
	    while (realname.hasMoreElements() && subname.hasMoreElements()) {
		String rname = realname.nextToken();
		String sname = subname.nextToken();
		DataSource source = new FileDataSource(fileDir + sname);
		dslist.add(source);
		rnlist.add(rname);
	    }
	    mailMap.put("title", title);
	    mailMap.put("email", writer);
	    mailMap.put("from", writer);
	    mailMap.put("to", email);
	    mailMap.put("cc", ccs);
	    mailMap.put("contents", contents);
	    mailMap.put("templateName", "userMail.ftl");
	    sendMailService.sendMail(mailMap, dslist, rnlist);
	    resultMap.put("msg", "메일이 발송 되었습니다.");
	}
	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "scheduleArticle.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> scheduleArticle(Locale locale,
	    Model model, @RequestParam("syear") String syear,
	    @RequestParam("smonth") String smonth,
	    @RequestParam("eyear") String eyear,
	    @RequestParam("emonth") String emonth) throws Exception {
	logger.info("Welcome scheduleArticle! The client locale is {}.", locale);

	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put("sDay", syear + "/" + smonth + "/01");
	paramMap.put("eDay", eyear + "/" + emonth + "/01");
	List<Map<String, String>> resultMap = homeService
		.getScheduleArticles(paramMap);

	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "scheduleFiles.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> scheduleFiles(Locale locale, Model model,
	    @RequestParam("seq") String seq) throws Exception {
	logger.info("Welcome scheduleArticle! The client locale is {}.", locale);

	List<Map<String, String>> resultMap = homeService.getScheduleFiles(seq);

	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "scheduleWrite.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> scheduleWrite(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("title") String title,
	    @RequestParam("endtime") String endtime,
	    @RequestParam("contents") String contents,
	    @RequestParam("starttime") String starttime,
	    @RequestParam("realnames") String realnames,
	    @RequestParam("subnames") String subnames,
	    @RequestParam("etcYn") String etcYn) throws Exception {
	logger.info("Welcome scheduleArticle! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();
	Map<String, Object> resultMap = new HashMap<String, Object>();
	String writer = (String) req.getSession().getAttribute(
		CommonUtil.SESSION_USER);
	StringTokenizer realname = new StringTokenizer(realnames, ",");
	StringTokenizer subname = new StringTokenizer(subnames, ",");

	int seq = homeService.getScheduleMaxSeq();
	paramMap.put("seq", "" + (seq + 1));
	paramMap.put("writer", writer);
	paramMap.put("title", title);
	paramMap.put("contents", contents);
	paramMap.put("starttime", starttime);
	paramMap.put("endtime", endtime);
	paramMap.put("etcYn", etcYn);
	resultMap.put("resultCnt", homeService.scheduleWrite(paramMap));
	while (realname.hasMoreElements() && subname.hasMoreElements()) {
	    Map<String, String> fileMap = new HashMap<String, String>();
	    String rname = realname.nextToken();
	    String sname = subname.nextToken();
	    fileMap.put("scheduleSeq", "" + (seq + 1));
	    fileMap.put("realname", rname);
	    fileMap.put("subname", sname);
	    resultMap.put("fileCnt", homeService.scheduleFileWrite(fileMap));
	}
	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "scheduleUpdate.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> scheduleUpdate(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("seq") String seq,
	    @RequestParam("title") String title,
	    @RequestParam("endtime") String endtime,
	    @RequestParam("contents") String contents,
	    @RequestParam("starttime") String starttime,
	    @RequestParam("realnames") String realnames,
	    @RequestParam("subnames") String subnames,
	    @RequestParam("etcYn") String etcYn) throws Exception {
	logger.info("Welcome scheduleUpdate! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();
	Map<String, Object> resultMap = new HashMap<String, Object>();
	String writer = (String) req.getSession().getAttribute(CommonUtil.SESSION_USER);
	StringTokenizer realname = new StringTokenizer(realnames, ",");
	StringTokenizer subname = new StringTokenizer(subnames, ",");
	paramMap.put("seq", seq);
	paramMap.put("writer", writer);
	paramMap.put("title", title);
	paramMap.put("contents", contents);
	paramMap.put("starttime", starttime);
	paramMap.put("endtime", endtime);
	paramMap.put("etcYn", etcYn);
	while (realname.hasMoreElements()) {
	    Map<String, String> fileMap = new HashMap<String, String>();
	    String rname = realname.nextToken();
	    String sname = subname.nextToken();
	    fileMap.put("scheduleSeq", seq);
	    fileMap.put("realname", rname);
	    fileMap.put("subname", sname);
	    resultMap.put("fileCnt", homeService.scheduleFileWrite(fileMap));
	}
	resultMap.put("resultCnt", homeService.scheduleUpdate(paramMap));
	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "scheduleDelete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> scheduleDelete(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("seq") String seq)
	    throws Exception {
	logger.info("Welcome scheculeDelete! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	String writer = (String) req.getSession().getAttribute(
		CommonUtil.SESSION_USER);
	paramMap.put("writer", writer);
	paramMap.put("seq", seq);

	Map<String, Object> resultMap = new HashMap<String, Object>();
	resultMap.put("fileCnt", homeService.scheduleDeleteFiles(paramMap));
	resultMap.put("resultCnt", homeService.scheduleDelete(paramMap));

	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "getArticle.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getArticle(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("seq") String seq)
	    throws Exception {
	logger.info("Welcome getArticle! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);
	Map<String, String> article = homeService.getArticle(paramMap);
	String user = (String) req.getSession().getAttribute(
		CommonUtil.SESSION_USER);
	if (article.get("receiver").equals(user)) {
	    homeService.updateArticle(paramMap);
	}
	return article;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "getSchedule.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getSchedule(Locale locale, Model model,
	    @RequestParam("seq") String seq, HttpServletRequest req)
	    throws Exception {
	logger.info("Welcome getSchedule! The client locale is {}.", locale);

	String user = (String) req.getSession().getAttribute(
		CommonUtil.SESSION_USER);
	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);
	Map<String, String> schedule = homeService.getSchedule(paramMap);
	String contents = schedule.get("contents");
	schedule.put("contents", contents);
	if (user.equals(schedule.get("email"))) {
	    schedule.put("isWriter", "true");
	}
	return schedule;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "getEtc.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getEtc(Locale locale, Model model,
	    @RequestParam("syear") String syear,
	    @RequestParam("smonth") String smonth,
	    @RequestParam("eyear") String eyear,
	    @RequestParam("emonth") String emonth) throws Exception {
	logger.info("Welcome getEtc! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("sDay", syear + "/" + smonth + "/01");
	paramMap.put("eDay", eyear + "/" + emonth + "/01");
	List<Map<String, String>> etcMap = homeService.getEtcList(paramMap);

	return etcMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "getFiles.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getFiles(Locale locale, Model model)
	    throws Exception {
	logger.info("Welcome getFiles! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	List<Map<String, String>> fileList = homeService.getFileList(paramMap);

	return fileList;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "getUserFiles.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getUserFiles(Locale locale, Model model,
	    @RequestParam("seq") String seq) throws Exception {
	logger.info("Welcome getUserFiles! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);
	List<Map<String, String>> fileList = homeService.getUserFiles(paramMap);

	return fileList;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "filedownload.do", method = RequestMethod.POST)
    public ModelAndView filedownload(Locale locale, Model model,
	    @RequestParam("seq") String seq) throws Exception {
	logger.info("Welcome filedownload! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);
	Map<String, String> fileMap = homeService.getFile(paramMap);
	return new ModelAndView("fileDownloadView", "downloadFile", fileMap);
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "userfiledownload.do", method = RequestMethod.POST)
    public ModelAndView userfiledownload(Locale locale, Model model,
	    @RequestParam("seq") String seq) throws Exception {
	logger.info("Welcome userfiledownload! The client locale is {}.",
		locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);
	Map<String, String> fileMap = homeService.getUserFile(paramMap);
	return new ModelAndView("fileDownloadView", "downloadFile", fileMap);
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "scheduledownload.do", method = RequestMethod.POST)
    public ModelAndView scheduledownload(Locale locale, Model model,
	    @RequestParam("seq") String seq) throws Exception {
	logger.info("Welcome userfiledownload! The client locale is {}.",
		locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);
	Map<String, String> fileMap = homeService.getScheduleFile(paramMap);
	return new ModelAndView("fileDownloadView", "downloadFile", fileMap);
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "filedelete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> filedelete(Locale locale, Model model,
	    HttpServletRequest req, @RequestParam("seq") String seq)
	    throws Exception {
	logger.info("Welcome filedelete! The client locale is {}.", locale);

	String email = (String) req.getSession().getAttribute(
		CommonUtil.SESSION_USER);
	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("seq", seq);
	Map<String, String> fileMap = homeService.getFile(paramMap);
	Map<String, Object> resultMap = new HashMap<String, Object>();
	resultMap.put("success", false);
	resultMap.put("msg", "등록자만 삭제 할 수 있습니다.");
	if (email.equals(fileMap.get("email"))) {
	    File file = new File(fileDir + fileMap.get("subname"));
	    if (file.isFile()) {
		boolean isDel = file.delete();
		if (isDel) {
		    int cnt = homeService.deleteFile(paramMap);
		    resultMap.put("cnt", cnt);
		    resultMap.put("success", true);
		    resultMap.put("msg", "삭제되었습니다.");
		}
	    }
	}
	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "fileUpload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> fileUpload(Locale locale, Model model,
	    HttpServletRequest req, HttpServletResponse res) throws Exception {
	logger.info("Welcome fileUpload! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();
	Map<String, Integer> resultMap = new HashMap<String, Integer>();

	DiskFileItemFactory factory = new DiskFileItemFactory();
	factory.setSizeThreshold(1024 * 1024 * 10);
	// factory.setRepository(new File("c:/upload/"));
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setSizeMax(1024 * 1024 * 100);
	upload.setHeaderEncoding("UTF-8");
	System.out.println(upload.getHeaderEncoding());
	List<FileItem> items = upload.parseRequest(req);
	System.out.println(items);
	Iterator<FileItem> iter = items.iterator();
	System.out.println(iter);
	while (iter.hasNext()) {
	    FileItem item = (FileItem) iter.next();
	    System.out.println(item.getName());
	    if (item.isFormField()) {
	    } else {
		if (item.getName() != null && !item.getName().equals("")) {
		    String subname = UUID.randomUUID().toString();
		    paramMap.put("realname", item.getName());
		    paramMap.put("email", (String) req.getSession()
			    .getAttribute(CommonUtil.SESSION_USER));
		    paramMap.put("subname", subname);
		    System.out.println(fileDir + subname);
		    File file = new File(fileDir + subname);
		    item.write(file);
		    System.out.println(file.getPath());
		}
	    }
	}

	resultMap.put("successCnt", homeService.fileWrite(paramMap));
	return resultMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "mailFileUpload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> mailFileUpload(Locale locale, Model model,
	    HttpServletRequest req, HttpServletResponse res) {
	logger.info("Welcome mailFileUpload! The client locale is {}.", locale);
	Map<String, String> paramMap = new HashMap<String, String>();

	DiskFileItemFactory factory = new DiskFileItemFactory();
	factory.setSizeThreshold(1024 * 1024 * 10);
	// factory.setRepository(new File("c:/upload/"));
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setSizeMax(1024 * 1024 * 10);
	upload.setHeaderEncoding("UTF-8");
	System.out.println(upload.getHeaderEncoding());
	List<FileItem> items;
	try {
	    items = upload.parseRequest(req);
	    Iterator<FileItem> iter = items.iterator();
	    while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		if (item.isFormField()) {
		} else {
		    if (item.getName() != null && !item.getName().equals("")) {
			String subname = UUID.randomUUID().toString();
			paramMap.put("realname", item.getName());
			paramMap.put("email", (String) req.getSession()
				.getAttribute(CommonUtil.SESSION_USER));
			paramMap.put("subname", subname);
			File file = new File(fileDir + subname);
			item.write(file);
			System.out.println(file.getPath());
		    } else {
			paramMap.put("error", "파일을 업로드할 수 없습니다.");
			return paramMap;
		    }
		}
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    paramMap.put("error", "10MB 이하의 파일만 업로드 가능합니다.");
	    return paramMap;
	}

	return paramMap;
    }

    /**
     * Simply selects the home view to render by returning its name.
     * @throws IOException 
     * 
     * @throws Exception
     */
    @RequestMapping(value = "imgupload.do", method = RequestMethod.POST)
    public void imgupload(Locale locale, Model model,  HttpServletRequest req, HttpServletResponse res) throws IOException {
	logger.info("Welcome mailFileUpload! The client locale is {}.", locale);
	DiskFileItemFactory factory = new DiskFileItemFactory();
	factory.setSizeThreshold(1024 * 1024 * 10);
	// factory.setRepository(new File("c:/upload/"));
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setSizeMax(1024 * 1024 * 10);
	upload.setHeaderEncoding("UTF-8");
	System.out.println(upload.getHeaderEncoding());
	List<FileItem> items;
	String callback_func = "";
	String realname = "";
	String imgName = "";
	try {
	    items = upload.parseRequest(req);
	    Iterator<FileItem> iter = items.iterator();
	    while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		if (item.isFormField()) {
		    if(item.getFieldName().equals("callback_func")){
	    		callback_func = item.getString();
		    }
		} else {
		    if (item.getName() != null && !item.getName().equals("")) {
			String ranName = UUID.randomUUID().toString();
			realname = item.getName();
			System.out.println(item.getName());
			imgName = ranName+"."+item.getName().split("\\.")[1];
			File file = new File(imageDir+imgName);
			item.write(file);
			System.out.println(file.getPath());
		    }
		}
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    logger.debug("image upload exception");
	}
	
	res.sendRedirect(req.getContextPath()+"/resources/SE/photo_uploader/popup/callback.html?sFileName="+realname+"&callback_func="+callback_func+"&bNewLine=true&sFileURL="+req.getContextPath()+"/resources/images/"+imgName);
    }

    /**
     * Simply selects the home view to render by returning its name.
     * 
     * @throws Exception
     */
    @RequestMapping(value = "getChart.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getChart(Locale locale, Model model)
	    throws Exception {
	logger.info("Welcome getUserFiles! The client locale is {}.", locale);

	Map<String, String> paramMap = new HashMap<String, String>();
	List<Map<String, String>> chartList = homeService.getChart(paramMap);

	return chartList;
    }

}
