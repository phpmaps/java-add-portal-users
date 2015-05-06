package com.sdk;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


@WebServlet("/Invite")

public class InviteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public InviteServlet() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String result = null;
		
		String clientEmail = null;
		
		clientEmail = request.getParameter("email");
		
		boolean isValid = isEmailValid(clientEmail);
		
		if(isValid){
			
			try {

				Token authenticate = new Token();
				
				String token = authenticate.getToken();
				
				Roles roles = new Roles(token);
				
				String defaultRole = roles.getDefaultRoleId();
				
				Self account = new Self(token);
				
				Map<String,String> params = account.getInvitationParams();
				
				params.put("client_email", clientEmail);
				
				params.put("default_role", defaultRole);
				
				Invite invite = new Invite(token, params);
				
				result = invite.doInvite();

			} catch (Exception e) {

				e.printStackTrace();
				
				System.out.println("Error: " + e);
				
			}
			
		}else{
			
			JSONObject error = new JSONObject();
			
			error.put("success", false);
			
			result = error.toString();
						
		}
		
		response.setContentType("application/json");
		
		response.setDateHeader("Last-Modified",(System.currentTimeMillis()/1000*1000));
		
		response.setCharacterEncoding("UTF-8"); 
		
		response.getWriter().write(result);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	public boolean isEmailValid(String email){
		
		boolean result = false;
		
	    if (email == null){
	    	
	    	return result;
	    	
	    }
	    
	    String[] tokens = email.split("@");
	    
	    if(tokens.length == 2 && !tokens[0].isEmpty() && !tokens[1].isEmpty()){
	    	
	    	result = true;
	    	
	    }
	    
	    return result;
	    
	}

}