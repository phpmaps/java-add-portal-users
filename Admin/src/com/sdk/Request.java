package com.sdk;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.util.List;

import org.apache.http.HttpEntity;

import org.apache.http.NameValuePair;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.client.utils.URLEncodedUtils;

import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;

import org.json.JSONObject;



public class Request {
	
	public List<NameValuePair> params;
	
	public String url;

	public Request(String u, List<NameValuePair> p) {
		
		url = u;
		
		params = p;
		
	}
	
	public String runGet() throws ClientProtocolException, IOException{
		
		String output = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
        try {

        	String paramsString = URLEncodedUtils.format(params, "UTF-8");
        	
        	HttpGet httpGet = new HttpGet(url + "?" + paramsString);
        	
            CloseableHttpResponse response = httpclient.execute(httpGet);
            
            try {
            	
                HttpEntity entity = response.getEntity();
                
                output = getBody(response,entity);
                
                EntityUtils.consume(entity);
                
            } finally {
            	
                response.close();
                
            }
            
        } finally {
        	
            httpclient.close();
            
        }
        
        return output;	
	}
	
	public String runPost() throws ClientProtocolException, IOException{
		
		String output = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
        try {

            HttpPost httpPost = new HttpPost(url);
            
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            
            CloseableHttpResponse response = httpclient.execute(httpPost);

            try {
            	
                HttpEntity entity = response.getEntity();
                
                output = getBody(response,entity);
                
                EntityUtils.consume(entity);
                
            } finally {
            	
                response.close();
                
            }
            
        } finally {
        	
            httpclient.close();
            
        }
        
		return output;
	}
	
	public String getBody(CloseableHttpResponse response, HttpEntity entity){
		
		String body = null;
		
		try {
		
			if (response.getStatusLine().getStatusCode() != 200) {	
				
				JSONObject json = new JSONObject();
				
				json.put("errors", "Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				
				json.put("messages","This error can from the Java client when requesting ArcGIS resources.");
				
				body = json.toString();
				
			}else{
				
				BufferedReader br;
				
				br = new BufferedReader(new InputStreamReader((entity.getContent())));
				
				StringBuilder str = new StringBuilder();
				
				String output;
				
				while ((output = br.readLine()) != null) {
					
					str.append(output);
					
				}
				
				body = str.toString();
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return body;
	}

}
