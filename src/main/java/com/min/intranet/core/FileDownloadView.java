package com.min.intranet.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownloadView extends AbstractView {

    @Value("${niee.fileDir}")
    private String fileDir;

    private static final Logger logger = LoggerFactory
	    .getLogger(FileDownloadView.class);

    public FileDownloadView() {
	setContentType("application/download; charset=utf-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map,
	    HttpServletRequest req, HttpServletResponse res) {
	// TODO Auto-generated method stub
	@SuppressWarnings("unchecked")
	Map<String, String> fileMap = (Map<String, String>) map
		.get("downloadFile");
	File file = new File(fileDir + fileMap.get("subname"));

	res.setContentType(getContentType());
	res.setContentLength((int) file.length());

	String userAgent = req.getHeader("User-Agent");
	boolean ie = userAgent.indexOf("MSIE") > -1;
	if (!ie) {
	    ie = (userAgent.indexOf("Trident") > -1 && userAgent.indexOf("rv") > -1);
	}
	String fileName = null;
	OutputStream out = null;
	FileInputStream fis = null;
	try {
	    if (ie) {
		fileName = URLEncoder.encode(fileMap.get("realname"), "utf-8")
			.replaceAll("\\+", "%20");
		logger.info(fileName);
	    } else {
		fileName = new String(
			fileMap.get("realname").getBytes("utf-8"), "iso-8859-1");
	    }
	    res.setHeader("Content-Disposition", "attachment; filename=\""
		    + fileName + "\";");
	    res.setHeader("Content-Transfer-Encoding", "binary");

	    out = res.getOutputStream();

	    fis = new FileInputStream(file);
	    FileCopyUtils.copy(fis, out);
	    out.flush();
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    if (fis != null)
		try {
		    fis.close();
		} catch (IOException ex) {
		}
	    if (out != null)
		try {
		    out.close();
		} catch (IOException ex) {
		}
	}
    }
}
