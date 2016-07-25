package com.tim.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.tim.exception.ConfigFileException;

public class SigConfig {
	
	public static String identifier;
	public static long sdkappid;
	public static long expire;
	public static String privateKey;
	
	private SigConfig() {
		
	}
	
	public static void loadConfig(String config) throws ConfigFileException {
		Properties prop =  new  Properties();
		try {
			FileInputStream in = new FileInputStream(config);
			prop.load(in);
		} catch (IOException e) {
			throw new ConfigFileException("invalid config file path");
		}

		identifier = prop.getProperty( "identifier" ).trim();
		privateKey = prop.getProperty( "privateKey" ).trim();
		
		try {
			sdkappid = Long.parseLong(prop.getProperty( "sdkappid" ).trim());
		} catch (java.lang.NumberFormatException e) {
			throw new ConfigFileException("invalid sdkappid");
		}
		
		try {
			expire = Long.parseLong(prop.getProperty( "expire" ).trim());
		} catch (java.lang.NumberFormatException e) {
			throw new ConfigFileException("invalid expire time");
		}
		
	}
}
