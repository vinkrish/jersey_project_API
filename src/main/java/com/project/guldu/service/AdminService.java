package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.project.guldu.model.AuthResponse;
import com.project.guldu.model.Credentials;
import com.project.guldu.model.School;

import authentication.TokenGenerator;

public class AdminService {
	Statement stmt = null;
	SchoolService schoolService;

	public AdminService() {
		try {
			stmt = JDBC.getConnection().createStatement();
			schoolService = new SchoolService();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Response authenticateUser(Credentials credentials) {
		AuthResponse auth = null;
		try {
			authenticate(credentials.getUsername(), credentials.getPassword());
			String token = issueToken(credentials.getUsername());
			saveToken(credentials.getUsername(), token);
			School school = schoolService.getSchoolByUserName(credentials.getUsername());
			auth = new AuthResponse(school.getId(), school.getSchoolName(), token);
			return Response.ok(auth).build();
			//return Response.status(200).entity(auth).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}      
	}
		
		private void authenticate(String username, String password) throws Exception {
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
			TokenGenerator generator = new TokenGenerator();
			return generator.getToken();
		}
		
		private void saveToken(String user, String token) {
			try {
				String query = "insert into authorization(User, Token) "
						+ "values ('"
						+ user + "','" 
						+ token + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

}
