package com.sdk;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;

import org.json.JSONObject;


public class Token {
	
	public String token = null;
	
	public String username = "doogle_startups";
	
	public String password = "Penny912";
	
	public String url = "https://arcgis.com/sharing/rest/generateToken";
	
	public String referer = "https://www.arcgis.com";
	
	public String f = "json";
	
	public String output = null;

	public Token() {

		try {
			
			this.authenticate();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	public void authenticate() throws Exception, IOException{
		
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		
		params.add(new BasicNameValuePair("username", username));
		
		params.add(new BasicNameValuePair("password", password));
		
		params.add(new BasicNameValuePair("referer", referer));
		
		params.add(new BasicNameValuePair("f", f));
        
        Request request = new Request(url,params);
        
        String response = request.runPost();
        
        output = response;
	}
	
	public String getToken(){
		
		JSONObject obj = new JSONObject(output);
		
		token = obj.getString("token");
		
		if(token == null){
			
			token = "Bad_Token_Not_Authenticated";
		}
		
		return token;
	}

}
