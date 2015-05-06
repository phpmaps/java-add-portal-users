package com.sdk;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

import java.util.Map;

import org.apache.http.NameValuePair;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;

import org.json.JSONObject;


public class Invite {
	
	public String homeUrl = null;
	
	public String prefixUrl = "maps.arcgis.com";
	
	public String suffexUrl = "/sharing/rest/portals/self/invite";
	
	//public String suffexUrl = "maps.arcgis.com/sharing/rest/portals/self/invite";
	
	public String url = null;
	
	public String token = null;
	
	public Map<String,String> params = null;
	
	public String f = "json";

	public Invite(String t, Map<String,String> dict) {
		
		token = t;
		
		params = dict;
		
		url = String.format("http://%s."+prefixUrl + suffexUrl, params.get("urlKey"));
		
		homeUrl = String.format("http://%s."+prefixUrl, params.get("urlKey"));
	}
	
	public String doInvite() throws ClientProtocolException, IOException {
		
		String orgName = params.get("orgName");
		
		String orgFullName = params.get("orgFullName");
		
		String adminEmail = params.get("adminEmail");

		String subject = "Invite to join ArcGIS Portal from your IBI platform";
		
		String clientEmail = params.get("client_email");
		
		String defaultRole = params.get("default_role");
		
		String htmlStart = "<html><body>";
		
		String htmlEnd = "</body></html>";
		
		String body = htmlStart
				+ "<p>" + orgFullName + " has invited you to join the " + orgName + " ArcGIS Portal organization.  "
				+ "ArcGIS is a collaborative, platform that allows members of an organization to use, create, and share maps, data, scenes and apps, and access authoritative basemaps and ready-to-use apps.  "
				+ "Please click this link to join:<br><br>"
				+ "<a href=\"https://www.arcgis.com/home/signin.html?invitation=@@invitation.id@@\">https://www.arcgis.com/home/signin.html?invitation=@@invitation.id@@</a><br>"
				+ "<p>If you have difficulty signing in, please email your administrator at " + adminEmail + ". </p>"
				+ "<p>At anytime you can access your new ArcGIS Portal account here: <br>" + homeUrl + "</p>"
				+ "<p>This link will expire in two weeks.</p><p style=\"color:gray;\">This is an automated email, please do not reply.</p>"
				+ htmlEnd;
		
		JSONObject invitations = new JSONObject();
		
		/*
		 * Assign username and temp password.  User does not receive an email. TODO: Make password stick.
		 * 		
		JSONObject user = new JSONObject();
		user.put("username", "Deigo_Guerrero");		
		user.put("password", "Password123");		
		user.put("firstname", "Deigo");		
		user.put("lastname", "Guerrero");		
		user.put("fullname", "Deigo Guerrero");	
		user.put("email", "dougbcarroll@gmail.com");	
		user.put("role", "account_user");
		*/
		
		JSONObject user = new JSONObject();
		
		user.put("email", clientEmail);
		
		user.put("role", defaultRole);

		JSONArray invitations_array = new JSONArray();
		
		invitations_array.put(user);
		
		invitations.put("invitations", invitations_array);
		
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		
		params.add(new BasicNameValuePair("subject", subject));
		
		params.add(new BasicNameValuePair("html", body));
		
		params.add(new BasicNameValuePair("invitationlist", invitations.toString()));
		
		params.add(new BasicNameValuePair("f", f));
		
		params.add(new BasicNameValuePair("token", token));
        
        Request request = new Request(url,params);
        
        String response = request.runPost();
		
		return response;
		
	}

}
