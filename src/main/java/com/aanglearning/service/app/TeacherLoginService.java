package com.aanglearning.service.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import com.aanglearning.authentication.TokenGenerator;
import com.aanglearning.model.Credentials;
import com.aanglearning.model.TeacherCredentials;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.service.DatabaseUtil;
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
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Response authenticateTeacher(Credentials credentials) {
		TeacherCredentials teacherCredentials = null;
		String token = "";
		try {
			if(authenticate(credentials.getUsername(), credentials.getPassword())) {
				token = getToken(credentials.getUsername());
				if(token.equals("")) {
					token = issueToken(credentials.getUsername());
					saveToken(credentials.getUsername(), token);
				}
				teacherCredentials = new TeacherCredentials();
				teacherCredentials.setMobileNo(credentials.getUsername());
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
	
	public Response authenticateCredentials(Credentials credentials, String appName) {
		TeacherCredentials teacherCredentials = null;
		String token = "";
		try {
			if(authenticate(credentials.getUsername(), credentials.getPassword())) {
				token = getToken(credentials.getUsername());
				if(token.equals("")) {
					token = issueToken(credentials.getUsername());
					saveToken(credentials.getUsername(), token);
				}
				teacherCredentials = new TeacherCredentials();
				teacherCredentials.setMobileNo(credentials.getUsername());
				teacherCredentials.setAuthToken(token);
				Teacher teacher = teacherService.getTeacher(credentials.getUsername());
				authorize(teacher.getId(), appName);
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
		String token = "";
		try {
			if(isPrincipal(credentials.getUsername(), credentials.getPassword())) {
				token = getToken(credentials.getUsername());
				if(token.equals("")) {
					token = issueToken(credentials.getUsername());
					saveToken(credentials.getUsername(), token);
				}
				teacherCredentials = new TeacherCredentials();
				teacherCredentials.setMobileNo(credentials.getUsername());
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
	
	private boolean authorize(long userId, String appName) throws Exception {
		String query = "select * from app_user where UserId = " + userId + " and AppName = '" + appName + "'";
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new Exception();
	}
	
	private boolean isPrincipal(String username, String password) throws Exception {
		String query = "select t.Username, t.Password from teacher t, school s where "
				+ "(s.Mobile1 = '" + username + "' and t.Username='" + username + "') or (s.Mobile2 = '" + username + "' and t.Username='" + username + "')" ;
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
	
	private String getToken(String user) {
		String token = "";
		String query = "select Token from authorization where User = '" + user + "'";
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				token = rs.getString("Token");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return token;
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
