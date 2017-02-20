package com.aanglearning.service.parent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

import com.aanglearning.authentication.TokenGenerator;
import com.aanglearning.model.ChildInfo;
import com.aanglearning.model.Credentials;
import com.aanglearning.model.ParentCredentials;
import com.aanglearning.service.JDBC;

public class LoginService {
	Statement stmt = null;
	
	public LoginService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Response authenticateUser(Credentials credentials) {
		ParentCredentials parentCredentials = null;
		try {
			if(authenticate(credentials.getUsername(), credentials.getPassword())) {
				String token = issueToken(credentials.getUsername());
				saveToken(credentials.getUsername(), token);
				parentCredentials = new ParentCredentials();
				parentCredentials.setToken(token);
				parentCredentials.setInfo(getChildInfo(credentials.getUsername()));
				return Response.ok(parentCredentials).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}      
	}
		
	private boolean authenticate(String mobile, String password) throws Exception {
		String query = "select Password from student where Mobile1 = '" + mobile + "'";
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
	
	private ArrayList<ChildInfo> getChildInfo(String mobile) {
		String query = "select A.SchoolId, A.SectionId, A.StudentName, B.SchoolName, C.SectionName, D.Id, D.ClassName "
				+ "from student A, school B, section C, class D  "
				+ "where A.Mobile1 = '" + mobile + "' and A.SchoolId = B.Id and A.SectionId = C.Id and C.ClassId = D.Id";
		ArrayList<ChildInfo> infos = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				ChildInfo info = new ChildInfo();
				info.setSchoolId(rs.getLong("A.SchoolId"));
				info.setSchoolName(rs.getString("B.SchoolName"));
				info.setClassId(rs.getLong("D.Id"));
				info.setClassName(rs.getString("D.ClassName"));
				info.setSectionId(rs.getLong("A.SectionId"));
				info.setSectionName(rs.getString("C.SectionName"));
				info.setName(rs.getString("A.StudentName"));
				infos.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	private String issueToken(String username) {
		TokenGenerator generator = new TokenGenerator();
		return generator.getToken();
	}

	private void saveToken(String mobile, String token) {
		try {
			String query = "insert into authorization(User, Token) "
					+ "values ('" + mobile + "','" + token + "')";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
