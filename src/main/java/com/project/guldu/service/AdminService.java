package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.project.guldu.model.Credentials;

public class AdminService {
	Statement stmt = null;

	public AdminService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Response authenticateUser(Credentials credentials) {

		try {
		
			// Authenticate the user using the credentials provided
			authenticate(credentials.getUsername(), credentials.getPassword());
			
			// Issue a token for the user
			String token = issueToken(credentials.getUsername());
			
			// Return the token on the response
			return Response.ok(token).build();
		
			} catch (Exception e) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}      
		}
		
		private void authenticate(String username, String password) throws Exception {
		// Authenticate against a database, LDAP, file or whatever
		// Throw an Exception if the credentials are invalid
			String query = "select AdminPassword from school where AdminUsername = '" + username + "'";
			String validatedPasswrod = "";
			try {
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()){
					validatedPasswrod = rs.getString("AdminPassword");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (!password.equals(validatedPasswrod)) {
				throw new Exception();
			}
		}
		
		private String issueToken(String username) {
		// Issue a token (can be a random String persisted to a database or a JWT token)
		// The issued token must be associated to a user
		// Return the issued token
			return "aljsdlfjajklkjmlkjadaeraiojuajlkjasldjfalksdjflajsdflasjdflkajsdl";
		}

}
