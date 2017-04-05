package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Teacher;
import com.aanglearning.service.JDBC;

public class TeacherService {
	Statement stmt = null;

	public TeacherService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Teacher getTeacher(String username) {
		String query = "select * from teacher where Username = '" + username + "'";
		Teacher teacher = new Teacher();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				teacher.setId(rs.getLong("Id"));
				teacher.setTeacherName(rs.getString("TeacherName"));
				teacher.setImage(rs.getString("Image"));
				teacher.setSchoolId(rs.getLong("SchoolId"));
				teacher.setDateOfBirth(rs.getString("DateOfBirth"));
				teacher.setMobile(rs.getString("Mobile"));
				teacher.setQualification(rs.getString("Qualification"));
				teacher.setDateOfJoining(rs.getString("DateOfJoining"));
				teacher.setGender(rs.getString("Gender"));
				teacher.setEmail(rs.getString("Email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher;
	}
	
	public List<Teacher> getSchoolTeachers(long schoolId) {
		String query = "select * from teacher where SchoolId = " + schoolId;
		return getTeacherList(query);
	}
	
	public List<Teacher> getClassSubjectTeachers(long classId) {
		String query = "select * from teacher where Id in "
				+ "(select TeacherId from subject_teacher where SectionId in (select Id from section where ClassId = " + classId + "))";
		return getTeacherList(query);
	}
	
	public List<Teacher> getSectionSubjectTeachers(long sectionId) {
		String query = "select * from teacher where Id in (select TeacherId from subject_teacher where SectionId = " + sectionId + ")";
		return getTeacherList(query);
	}
	
	public List<Teacher> getClassGroupUsers(long groupId, long classId) {
		String query = "select * from teacher where Id not in (select UserId from user_group where Role='teacher' and GroupId="+groupId+") and Id in "
				+ "(select TeacherId from subject_teacher where SectionId in (select Id from section where ClassId = " + classId + "))";
		return getTeacherList(query);
	}
	
	public List<Teacher> getSectionGroupUsers(long groupId, long sectionId) {
		String query = "select * from teacher where Id not in (select UserId from user_group where Role='teacher' and GroupId="+groupId+") and Id in "
				+ "(select TeacherId from subject_teacher where SectionId = " + sectionId + ")";
		return getTeacherList(query);
	}
	
	public List<Teacher> getTeacherList(String query) {
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
