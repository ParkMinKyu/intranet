package com.min.intranet.controller;

import java.io.File;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.min.intranet.core.SendMailService;
import com.min.intranet.service.EmployeeService;

@Controller
@RequestMapping("/home")
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Value("${niee.fileDir}")
	private String fileDir;

	@Value("${niee.imageDir}")
	private String imageDir;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SendMailService sendMailService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "company.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> company(Locale locale, Model model, @RequestParam("seq") String seq)
			throws Exception {
		logger.info("Welcome company! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);

		List<Map<String, String>> company = employeeService.getCmpanyList(paramMap);

		return company;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "department.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> department(Locale locale, Model model, @RequestParam("seq") String seq)
			throws Exception {
		logger.info("Welcome department! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);

		List<Map<String, String>> department = employeeService.getCmpanyList(paramMap);

		return department;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "employee.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> user(Locale locale, Model model, @RequestParam("seq") String seq)
			throws Exception {
		logger.info("Welcome employee! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);
		List<Map<String, String>> employee = employeeService.getEmployeeList(paramMap);

		return employee;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "userArticle.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> userArticle(Locale locale, Model model, HttpServletRequest req) throws Exception {
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

		List<Map<String, String>> userArticle = employeeService.getUserArticles(paramMap);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("count", employeeService.getUserArticlesCount(paramMap));
		resultMap.put("page", page);
		resultMap.put("articles", userArticle);

		return resultMap;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "getArticle.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getArticle(Locale locale, Model model, HttpServletRequest req,Authentication auth,
			@RequestParam("seq") String seq) throws Exception {
		logger.info("Welcome getArticle! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);
		Map<String, String> article = employeeService.getArticle(paramMap);
		String user = auth.getName();
		if (article.get("receiver").equals(user)) {
			employeeService.updateArticle(paramMap);
		}
		return article;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "sendUserMail.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendUserMail(Locale locale, Model model, HttpServletRequest req,Authentication auth,
			@RequestParam("title") String title, @RequestParam("contents") String contents,
			@RequestParam("email") String email, @RequestParam("isSend") boolean isSend,
			@RequestParam("realnames") String realnames, @RequestParam("subnames") String subnames,
			@RequestParam("ccs") String ccs) throws Exception {
		logger.info("Welcome sendUserMail! The client locale is {}.", locale);
		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String writer = auth.getName();
		StringTokenizer realname = new StringTokenizer(realnames, ",");
		StringTokenizer subname = new StringTokenizer(subnames, ",");
		StringTokenizer cclist = new StringTokenizer(ccs, ",");

		int seq = employeeService.getMaxSeq();
		paramMap.put("seq", "" + (seq + 1));
		paramMap.put("writer", writer);
		paramMap.put("receiver", email);
		paramMap.put("title", title);
		paramMap.put("contents", contents);
		resultMap.put("resultCnt", employeeService.userArticleWrite(paramMap));
		resultMap.put("msg", "등록 되었습니다.");
		while (realname.hasMoreElements() && subname.hasMoreElements()) {
			Map<String, String> fileMap = new HashMap<String, String>();
			String rname = realname.nextToken();
			String sname = subname.nextToken();
			fileMap.put("userseq", "" + (seq + 1));
			fileMap.put("realname", rname);
			fileMap.put("subname", sname);
			int cnt = employeeService.userFileWrite(fileMap);
			if (cnt == 0)
				resultMap.put("msg", "파일 등록 실패");
		}

		while (cclist.hasMoreElements()) {
			String cc = cclist.nextToken();
			seq = employeeService.getMaxSeq();
			paramMap.put("seq", "" + (seq + 1));
			paramMap.put("writer", writer);
			paramMap.put("receiver", cc);
			paramMap.put("title", title);
			paramMap.put("contents", contents);
			resultMap.put("resultCnt", employeeService.userArticleWrite(paramMap));
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
				int cnt = employeeService.userFileWrite(fileMap);
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
	@RequestMapping(value = "getUserFiles.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getUserFiles(Locale locale, Model model, @RequestParam("seq") String seq)
			throws Exception {
		logger.info("Welcome getUserFiles! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);
		List<Map<String, String>> fileList = employeeService.getUserFiles(paramMap);

		return fileList;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "userfiledownload.do", method = RequestMethod.POST)
	public ModelAndView userfiledownload(Locale locale, Model model, @RequestParam("seq") String seq) throws Exception {
		logger.info("Welcome userfiledownload! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);
		Map<String, String> fileMap = employeeService.getUserFile(paramMap);
		return new ModelAndView("fileDownloadView", "downloadFile", fileMap);
	}

	@RequestMapping(value = "userFileDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userFileDelete(Locale locale, Model model, @RequestParam("name") String name)
			throws Exception {
		logger.info("Welcome userFileDelete! The client locale is {}.", locale);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		File file = new File(fileDir + name);
		if (file.isFile()) {
			resultMap.put("isDel", file.delete());
		}
		return resultMap;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "mailFileUpload.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> mailFileUpload(Locale locale, Model model, HttpServletRequest req,Authentication auth,
			HttpServletResponse res) {
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
						paramMap.put("email", auth.getName());
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
			e.printStackTrace();
			paramMap.put("error", "10MB 이하의 파일만 업로드 가능합니다.");
			return paramMap;
		}

		return paramMap;
	}

}
