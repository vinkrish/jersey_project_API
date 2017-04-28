package com.aanglearning.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.aanglearning.authentication.TokenGenerator;
import com.aanglearning.model.AuthResponse;
import com.aanglearning.model.Credentials;
import com.aanglearning.model.entity.School;
import com.aanglearning.service.entity.SchoolService;

public class AdminService {
	Statement stmt;
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
	
	// Issue a token (can be a random String persisted to a database or a JWT token)
	private String issueToken(String username) {
		TokenGenerator generator = new TokenGenerator();
		return generator.getToken();
	}
	
	// The issued token must be associated to a user
	private void saveToken(String user, String token) {
		try {
			String query = "insert into authorization(User, Token) "
					+ "values ('" + user + "','" + token + "')";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
