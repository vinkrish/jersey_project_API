package com.aanglearning.service.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.aanglearning.authentication.TokenGenerator;
import com.aanglearning.model.Credentials;
import com.aanglearning.model.TeacherCredentials;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.service.JDBC;
import com.aanglearning.service.entity.SchoolService;
import com.aanglearning.service.entity.ServicesService;
import com.aanglearning.service.entity.TeacherService;

public class TeacherLoginService {
	Statement stmt;
	TeacherService teacherService = new TeacherService();
	SchoolService schoolService = new SchoolService();
	ServicesService servicesService = new ServicesService();
	
	public TeacherLoginService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Response authenticateUser(Credentials credentials) {
		TeacherCredentials teacherCredentials = null;
		try {
			if(authenticate(credentials.getUsername(), credentials.getPassword())) {
				//deleteToken(credentials.getUsername());
				String token = issueToken(credentials.getUsername());
				saveToken(credentials.getUsername(), token);
				teacherCredentials = new TeacherCredentials();
				teacherCredentials.setAuthToken(token);
				Teacher teacher = teacherService.getTeacher(credentials.getUsername());
				teacherCredentials.setTeacher(teacher);
				teacherCredentials.setSchoolId(teacher.getSchoolId());
				teacherCredentials.setSchoolName(schoolService.getSchoolById(teacher.getSchoolId()).getSchoolName());
				teacherCredentials.setService(servicesService.getService(teacher.getSchoolId()));
				return Response.ok(teacherCredentials).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}      
	}
	
	public Response authenticatePrincipal(Credentials credentials) {
		TeacherCredentials teacherCredentials = null;
		try {
			if(isPrincipal(credentials.getUsername(), credentials.getPassword())) {
				//deleteToken(credentials.getUsername());
				String token = issueToken(credentials.getUsername());
				saveToken(credentials.getUsername(), token);
				teacherCredentials = new TeacherCredentials();
				teacherCredentials.setAuthToken(token);
				Teacher teacher = teacherService.getTeacher(credentials.getUsername());
				teacherCredentials.setTeacher(teacher);
				teacherCredentials.setSchoolId(teacher.getSchoolId());
				teacherCredentials.setSchoolName(schoolService.getSchoolById(teacher.getSchoolId()).getSchoolName());
				teacherCredentials.setService(servicesService.getService(teacher.getSchoolId()));
				return Response.ok(teacherCredentials).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}      
	}
		
	private boolean authenticate(String username, String password) throws Exception {
		String query = "select Password from teacher where Username = '" + username + "'";
		String validatedPasswrod = "";
		boolean validPassword = false;
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				validatedPasswrod = rs.getString("Password");
				if (password.equals(validatedPasswrod)) {
					validPassword = true;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validPassword) {
			return true;
		} else {
			throw new Exception();
		}
	}
	
	private boolean isPrincipal(String username, String password) throws Exception {
		String query = "select AdminPassword from school where Mobile1 = '" + username + "' or Mobile2 = '" + username + "'" ;
		String validatedPasswrod = "";
		boolean validPassword = false;
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				validatedPasswrod = rs.getString("AdminPassword");
				if (password.equals(validatedPasswrod)) {
					validPassword = true;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validPassword) {
			return true;
		} else {
			throw new Exception();
		}
	}
	
	private void deleteToken(String mobileNo) {
		try {
			String query = "delete from authorization where User = '" + mobileNo + "'";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String issueToken(String username) {
		TokenGenerator generator = new TokenGenerator();
		return generator.getToken();
	}

	private void saveToken(String username, String token) {
		try {
			String query = "insert into authorization(User, Token) "
					+ "values ('" + username + "','" + token + "')";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
