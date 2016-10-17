package com.howell.action.response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicParam {
	private static Logger log = (Logger) LoggerFactory.getLogger(PublicParam.class);
	
	
	public static String getStandardJsonToStr(String json){
		JSONObject standardJson = new JSONObject(json); 
		
		standardJson.put("uid", "123");
		standardJson.put("phone", "13580478329");
		standardJson.put("has_password", true);
		
		return standardJson.toString();
	}
}
