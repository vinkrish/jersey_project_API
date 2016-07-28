package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.Teacher;

public class TeacherService {
	Statement stmt = null;

	public TeacherService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Teacher> getTeacherList(long schoolId) {
		String query = "select * from teacher where SchoolId = " + schoolId;
		List<Teacher> teacherList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Teacher teacher = new Teacher ();
				teacher.setId(rs.getLong("Id"));
				teacher.setTeacherName(rs.getString("TeacherName"));
				teacher.setImage(rs.getString("Image"));
				teacher.setUsername(rs.getString("Username"));
				teacher.setPassword(rs.getString("Password"));
				teacher.setSchoolId(rs.getLong("SchoolId"));
				teacher.setDateOfBirth(rs.getString("DateOfBirth"));
				teacher.setMobile(rs.getString("Mobile"));
				teacher.setQualification(rs.getString("Qualification"));
				teacher.setDateOfJoining(rs.getString("DateOfJoining"));
				teacher.setGender(rs.getString("Gender"));
				teacher.setEmail(rs.getString("Email"));
				teacherList.add(teacher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacherList;
	}
	
	public void addTeacher(String teacherStr){
		JSONArray teacherArray = new JSONArray(teacherStr);
		for (int i = 0; i < teacherArray.length(); i++) {
			JSONObject teacherJson = teacherArray.getJSONObject(i);
			Gson gson = new Gson();
			Teacher teacher = gson.fromJson(teacherJson.toString(), Teacher.class);
			try {
				String query = "insert into teacher(Id, TeacherName, Image, Username, Password, SchoolId, "
						+ "DateOfBirth, Mobile, Qualification, DateOfJoining, Gender, Email) "
						+ "values ("
						+ teacher.getId() + ",'" 
						+ teacher.getTeacherName() + "','"
						+ teacher.getImage() + "','"
						+ teacher.getUsername() + "','"
						+ teacher.getPassword() + "',"
						+ teacher.getSchoolId() + ",'"
						+ teacher.getDateOfBirth() + "','"
						+ teacher.getMobile() + "','"
						+ teacher.getQualification() + "','"
						+ teacher.getDateOfJoining() + "','"
						+ teacher.getGender() + "','"
						+ teacher.getEmail() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Teacher add(Teacher teacher) {
		try {
			String query = "insert into teacher(Id, TeacherName, Image, Username, Password, SchoolId, "
					+ "DateOfBirth, Mobile, Qualification, DateOfJoining, Gender, Email) "
					+ "values ("
					+ teacher.getId() + ",'" 
					+ teacher.getTeacherName() + "','"
					+ teacher.getImage() + "','"
					+ teacher.getUsername() + "','"
					+ teacher.getPassword() + "',"
					+ teacher.getSchoolId() + ",'"
					+ teacher.getDateOfBirth() + "','"
					+ teacher.getMobile() + "','"
					+ teacher.getQualification() + "','"
					+ teacher.getDateOfJoining() + "','"
					+ teacher.getGender() + "','"
					+ teacher.getEmail() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			teacher.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher;
	}
	
	public void update(Teacher teacher) {
		try {
			String query = "update teacher set TeacherName = '" + teacher.getTeacherName() 
			+ "', Username = '" + teacher.getUsername() 
			+ "', Password = '" + teacher.getPassword() 
			+ "', DateOfBirth = '" + teacher.getDateOfBirth() 
			+ "', Mobile = '" + teacher.getMobile() 
			+ "', Qualification = '" + teacher.getQualification() 
			+ "', DateOfJoining = '" + teacher.getDateOfJoining() 
			+ "', Gender = '" + teacher.getGender() 
			+ "', Email = '" + teacher.getEmail() + "' where Id = " + teacher.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long teacherId) {
		try {
			String query = "delete from teacher where Id=" + teacherId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
