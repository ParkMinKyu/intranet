package com.min.intranet.core;

import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service("defaultEncryptor")
public class DefaultEncryptor {
    	
    	private String getNieeSecurity(){
    	    return System.getProperty("niee.security");
    	}
	
	public String encode(String str){
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(getNieeSecurity());
		return encryptor.encrypt(str);
	}
	
	public String decode(String str){
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(getNieeSecurity());
		return encryptor.decrypt(str);
	}
	
	public String base64Encoding(String str){
		byte [] b64 = Base64.encodeBase64(str.getBytes());
		return new String(b64);
	}

	public String base64Decoding(String str){
		byte [] b64 = Base64.decodeBase64(str.getBytes());
		return new String(b64);
	}
}
