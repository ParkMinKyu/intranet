package com.min.intranet.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
import com.min.intranet.service.FileService;

@Controller
@RequestMapping("/home")
public class FileArticleController {
	private static final Logger logger = LoggerFactory.getLogger(FileArticleController.class);

	@Value("${niee.fileDir}")
	private String fileDir;

	@Value("${niee.imageDir}")
	private String imageDir;

	@Autowired
	private FileService fileService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "getFiles.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getFiles(Locale locale, Model model) throws Exception {
		logger.info("Welcome getFiles! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		List<Map<String, String>> fileList = fileService.getFileList(paramMap);

		return fileList;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "fileUpload.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> fileUpload(Locale locale, Model model, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
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
					paramMap.put("email", (String) req.getSession().getAttribute(CommonUtil.SESSION_USER));
					paramMap.put("subname", subname);
					System.out.println(fileDir + subname);
					File file = new File(fileDir + subname);
					item.write(file);
					System.out.println(file.getPath());
				}
			}
		}

		resultMap.put("successCnt", fileService.fileWrite(paramMap));
		return resultMap;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "filedownload.do", method = RequestMethod.POST)
	public ModelAndView filedownload(Locale locale, Model model, @RequestParam("seq") String seq) throws Exception {
		logger.info("Welcome filedownload! The client locale is {}.", locale);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);
		Map<String, String> fileMap = fileService.getFile(paramMap);
		return new ModelAndView("fileDownloadView", "downloadFile", fileMap);
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "filedelete.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> filedelete(Locale locale, Model model, HttpServletRequest req,
			@RequestParam("seq") String seq) throws Exception {
		logger.info("Welcome filedelete! The client locale is {}.", locale);

		String email = (String) req.getSession().getAttribute(CommonUtil.SESSION_USER);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq", seq);
		Map<String, String> fileMap = fileService.getFile(paramMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		resultMap.put("msg", "등록자만 삭제 할 수 있습니다.");
		if (email.equals(fileMap.get("email"))) {
			File file = new File(fileDir + fileMap.get("subname"));
			if (file.isFile()) {
				boolean isDel = file.delete();
				if (isDel) {
					int cnt = fileService.deleteFile(paramMap);
					resultMap.put("cnt", cnt);
					resultMap.put("success", true);
					resultMap.put("msg", "삭제되었습니다.");
				}
			}
		}
		return resultMap;
	}
}
