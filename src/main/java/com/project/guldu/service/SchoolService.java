package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.School;

public class SchoolService {
	Statement stmt = null;

	public SchoolService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public School getSchool(long schoolId) {
		String query = "select * from school where Id = " + schoolId;
		School school = new School();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				school.setId(rs.getLong("Id"));
				school.setSchoolName(rs.getString("SchoolName"));
				school.setWebsite(rs.getString("Website"));
				school.setShortenedSchoolName(rs.getString("ShortenedSchoolName"));
				school.setContactPersonName(rs.getString("ContactPersonName"));
				school.setAdminUsername(rs.getString("AdminUsername"));
				school.setAdminPassword(rs.getString("AdminPassword"));
				school.setLandline(rs.getString("Landline"));
				school.setMobile1(rs.getString("Mobile1"));
				school.setMobile2(rs.getString("Mobile2"));
				school.setEmail(rs.getString("Email"));
				school.setStreet(rs.getString("Street"));
				school.setCity(rs.getString("City"));
				school.setDistrict(rs.getString("District"));
				school.setState(rs.getString("State"));
				school.setPincode(rs.getString("Pincode"));
				school.setPrincipalId(rs.getLong("PrincipalId"));
				school.setNumberOfStudents(rs.getInt("NumberOfStudents"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return school;
	}
	
	public void addSchool (String schoolStr) {
		
		Gson gson1 = new Gson();
		School[] schools = gson1.fromJson(schoolStr, School [].class);
		
		JSONArray schoolArray = new JSONArray(schoolStr);
		for (int i = 0; i < schoolArray.length(); i++) {
			JSONObject schoolJson = schoolArray.getJSONObject(i);
			Gson gson = new Gson();
			School school = gson.fromJson(schoolJson.toString(), School.class);
			try {
				String query = "insert into school(Id, SchoolName, Website, ShortenedSchoolName, ContactPersonName, "
						+ "AdminUsername, AdminPassword, Landline, Mobile1, Mobile2, Email, Street, City, District, State, "
						+ "Pincode, PrincipalId, NumberOfStudents) "
						+ "values (" 
						+ school.getId() + ",'" 
						+ school.getSchoolName() + "','"
						+ school.getWebsite() + "','"
						+ school.getShortenedSchoolName() + "','"
						+ school.getContactPersonName() + "','"
						+ school.getAdminUsername() + "','"
						+ school.getAdminPassword() + "','"
						+ school.getLandline() + "','"
						+ school.getMobile1() + "','"
						+ school.getMobile2() + "','"
						+ school.getEmail() + "','"
						+ school.getStreet() + "','"
						+ school.getCity() + "','"
						+ school.getDistrict() + "','"
						+ school.getState() + "','"
						+ school.getPincode() + "',"
						+ school.getPrincipalId() + ","
						+ school.getNumberOfStudents() + ")";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
