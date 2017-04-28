package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.School;
import com.aanglearning.model.entity.Service;
import com.aanglearning.resource.entity.ServiceResource;
import com.aanglearning.service.JDBC;

public class SchoolService {
	Statement stmt;
	ServiceResource resource;

	public SchoolService() {
		try {
			stmt = JDBC.getConnection().createStatement();
			resource = new ServiceResource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public School getSchoolById(long schoolId) {
		String query = "select * from school where Id = " + schoolId;
		return getSchool(query);
	}
	
	public List<School> getSchools(String adminPassword) {
		String query = "select * from school";
		List<School> schools = new ArrayList<School>();
		if(adminPassword.equals("mysecretadminpassword")) {
			try {
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					School school = new School();
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
					schools.add(school);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return schools;
	}

	public School getSchoolByUserName(String username) {
		String query = "select * from school where AdminUsername = '" + username + "'";
		return getSchool(query);
	}

	private School getSchool(String query) {
		School school = new School();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
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

	public School add(School school) {

		// Gson gson1 = new Gson();
		// School[] schools = gson1.fromJson(schoolStr, School [].class);

		/*
		 * JSONArray schoolArray = new JSONArray(schoolStr); for (int i = 0; i <
		 * schoolArray.length(); i++) { JSONObject schoolJson =
		 * schoolArray.getJSONObject(i); Gson gson = new Gson(); School school =
		 * gson.fromJson(schoolJson.toString(), School.class); }
		 */

		try {
			String query = "insert into school(SchoolName, Website, ShortenedSchoolName, ContactPersonName, "
					+ "AdminUsername, AdminPassword, Landline, Mobile1, Mobile2, Email, Street, City, District, State, "
					+ "Pincode, PrincipalId, NumberOfStudents) " + "values ("
					+ "'" + school.getSchoolName() 
					+ "','" + school.getWebsite() 
					+ "','" + school.getShortenedSchoolName()
					+ "','" + school.getContactPersonName() 
					+ "','" + school.getAdminUsername() 
					+ "','" + school.getAdminPassword() 
					+ "','" + school.getLandline() 
					+ "','" + school.getMobile1() 
					+ "','" + school.getMobile2() 
					+ "','" + school.getEmail() 
					+ "','" + school.getStreet() 
					+ "','" + school.getCity() 
					+ "','" + school.getDistrict() 
					+ "','" + school.getState() 
					+ "','" + school.getPincode() 
					+ "'," + school.getPrincipalId() 
					+ "," + school.getNumberOfStudents() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) pk = rs.getLong(1);
			school.setId(pk);
			
			Service service = new Service();
			service.setSchoolId(school.getId());
			service.setIsMessage(true);
			resource.addService(service);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return school;
	}
	
	public void update(School school) {
		try {
			String query = "update school set"
					+ " where Id = " + school.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from school where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
