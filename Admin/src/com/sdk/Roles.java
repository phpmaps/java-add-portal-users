package com.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class Roles {
	
	public String url = "http://www.arcgis.com/sharing/rest/portals/self/roles";
	
	public String token = null;
	
	public String f = "json";

	public Roles(String t) {
		
		token = t;
	}
	
	public String getDefaultRoleId() throws Exception, IOException{
		
		String defaultRole = RoleNames.valueOf("USER").toString();
		
		return defaultRole;

	}
	
	public String getRoleId(String roleName) throws ClientProtocolException, IOException{
		
		String roleId = null;
		
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		
		params.add(new BasicNameValuePair("f", f));
		
		params.add(new BasicNameValuePair("token", token));
        
        Request request = new Request(url,params);
        
        String response = request.runGet();
        
        /*
         * Sample output:
         * 
         * {"total" : 1,"start" : 1,"num" : 10,"nextStart" : -1,"roles" : [{"id" : "K5zFLGrY1uC0QsLM","name" : "Intern","description" : "Special intern role for emerging business program.","created" : 1429455525000,"modified" : 1429455525000}]}
         * 
         */
        
        JSONObject obj = new JSONObject(response);
        
        JSONArray ja = obj.getJSONArray("roles");
        
        int i;
        
        for(i=0; i< ja.length(); i++){
        	
        	JSONObject item = (JSONObject) ja.get(i);
        	
        	String roleNameLower = roleName.toLowerCase();
        	
        	//System.out.print(item.getString("id"));
        	
        	
        	if(new String(roleNameLower).equals(item.getString("name").toLowerCase())){
        		
        		roleId = item.getString("id");
        		
        	}else if(new String(roleName.toLowerCase()).equals("administrator") || new String(roleName.toLowerCase()).equals(RoleNames.valueOf("ADMIN").toString())){
        		
        		roleId = RoleNames.valueOf("ADMIN").toString();
        		
        	}else if(new String(roleName.toLowerCase()).equals("publisher") || new String(roleName.toLowerCase()).equals(RoleNames.valueOf("PUBLISHER").toString())){
        		
        		roleId = RoleNames.valueOf("PUBLISHER").toString();
        		
        	}else if(new String(roleName.toLowerCase()).equals("user") || new String(roleName.toLowerCase()).equals(RoleNames.valueOf("USER").toString())){
        		
        		roleId = RoleNames.valueOf("USER").toString();
        		
        	}
        	
        }
        
		return roleId;

		
	}

}



