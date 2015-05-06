package com.sdk;

import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;

import org.json.JSONObject;

public class Self {
	
	public String url = "http://www.arcgis.com/sharing/rest/portals/self";
	
	public String token = null;
	
	public String f = "json";
	
	public String urlKey = null;
	
	public String name = null;
	
	public String fullName = null;
	
	public String email = null;
	
	public String output = null;
	

	public Self(String t) {
		
		token = t;
		
		try {
			
			this.accountInfo();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void accountInfo() throws Exception, IOException{
		
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		
		params.add(new BasicNameValuePair("f", f));
		
		params.add(new BasicNameValuePair("token", token));
        
        Request request = new Request(url,params);
        
        String response = request.runGet();
        
        setProperties(response);
        
	}
	
	public void setProperties(String response){
		
		output = response;
		
		JSONObject obj = new JSONObject(output);
		
		urlKey = obj.getString("urlKey");
		
		name = obj.getString("name");
		
		JSONObject user = obj.getJSONObject("user");
		
		fullName = user.getString("fullName");
		
		email = user.getString("email");
		
	}
	
	public Map<String,String> getInvitationParams(){
		
		Map<String, String> dict = new HashMap<String,String>();
		
		dict.put("urlKey", urlKey);
		
		dict.put("orgName", name);
		
		dict.put("orgFullName", fullName);
		
		dict.put("adminEmail", email);
			
		return dict;		
	}

}
