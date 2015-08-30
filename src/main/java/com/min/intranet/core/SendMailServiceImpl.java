package com.min.intranet.core;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service(value = "sendMailService")
public class SendMailServiceImpl implements SendMailService {

	private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);
	
	@Resource(name="mailSender")
	private JavaMailSenderImpl mailSender;
	
	private String createContents(Map<String, String> paramMap){
		Configuration cfg = new Configuration();
		TemplateLoader loader = new ClassTemplateLoader(getClass(), "../../../../mailtemplate");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateLoader(loader);
		
		String templateName = paramMap.get("templateName");
		String contents = "";
		try {
			Template template = cfg.getTemplate(templateName);
			
			Writer wr = new StringWriter();
			template.process(paramMap, wr);
			contents = wr.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;
	}
	
	@Override
	public void sendMail(Map<String, String> paramMap) {
		MimeMessage message = mailSender.createMimeMessage();
		// TODO Auto-generated method stub
		try {
			message.setSubject(paramMap.get("title"));
			message.setText(this.createContents(paramMap),"UTF-8","html");
			message.setFrom(new InternetAddress(paramMap.get("from")));
			message.addRecipient(RecipientType.TO, new InternetAddress(paramMap.get("to")));
			mailSender.send(message);
		} catch (MailException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendMail(Map<String, String> paramMap, List<DataSource> dslist,List<String> rnlist) {
		// TODO Auto-generated method stub
		MimeMessage message = mailSender.createMimeMessage();
		// TODO Auto-generated method stub
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
			helper.setSubject(paramMap.get("title"));
			helper.setText(this.createContents(paramMap),true);
			helper.setFrom(new InternetAddress(paramMap.get("from")));
			helper.setTo(new InternetAddress(paramMap.get("to")));
			logger.info(paramMap.get("cc"));
			if(paramMap.get("cc")!=null && !paramMap.get("cc").equals("")){
				helper.setCc(paramMap.get("cc").split(","));
			}
			for(int i = 0 ; i < dslist.size() ; i ++){
				helper.addAttachment(MimeUtility.encodeText(rnlist.get(i), "UTF-8", "B"), dslist.get(i));
			}
			
			mailSender.send(message);
		} catch (MailException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
