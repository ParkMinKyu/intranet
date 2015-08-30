package com.min.intranet.core;

import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

public class CustomEnvironmentStringPBEConfig extends EnvironmentStringPBEConfig {

    @Override
    public void setPassword(String password) {
        // TODO Auto-generated method stub
	String propertyPassword = System.getProperty(password) == null ? password : System.getProperty(password);
        super.setPassword(propertyPassword);
    }
    
}
