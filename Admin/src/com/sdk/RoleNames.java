package com.sdk;

public enum RoleNames {
	 
	ADMIN("account_admin"), 
	
	PUBLISHER("account_publisher"), 
	
	USER("account_user");
 
	private String role;
 
	private RoleNames(String role) {
		
		this.role = role;
		
	}
 
	public String getRole() {
		
		return role;
		
	}
 
}