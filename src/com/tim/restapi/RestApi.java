package com.tim.restapi;

import org.json.JSONObject;

import com.tim.config.SigConfig;
import com.tim.exception.ConfigFileException;
import com.tim.exception.SigException;

public class RestApi {
	
	public void SetConfigFile(String config) {
		try {
			SigConfig.loadConfig(config);
		} catch (ConfigFileException e) {
			// TODO Auto-generated catch block
//			LogSystem.logsys(String.format("ConfigFileException:%s", e.toString()));
			e.printStackTrace();
		}
	}
	
	public JSONObject DoRequest(String serviceName, String cmdName, JSONObject reqBody) {
		JSONObject json = null;
		try {
			json = HttpPost.doPost(serviceName, cmdName, reqBody);
		} catch (SigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
}
