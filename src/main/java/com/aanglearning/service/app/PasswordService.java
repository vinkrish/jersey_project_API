package com.aanglearning.service.app;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.aanglearning.model.entity.Student;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.resource.entity.StudentResource;
import com.aanglearning.resource.entity.TeacherResource;
import com.aanglearning.service.DatabaseUtil;

public class PasswordService {
	
	Connection connection;
	StudentResource studentResource = new StudentResource();
	TeacherResource teacherResource = new TeacherResource();
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	public PasswordService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	String randomString(int len){
	   StringBuilder sb = new StringBuilder(len);
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt(rnd.nextInt(AB.length())));
	   return sb.toString();
	}
	
	public void updateClassPswd(long classId) {
		List<Student> students = studentResource.getStudents("select * from student where ClassId = " + classId);
		updateStudentsPswd(students);
	}
	
	public void updateSectionPswd(long sectionId) {
		List<Student> students = studentResource.getStudents("select * from student where SectionId = " + sectionId);
		updateStudentsPswd(students);
	}
	
	public void updateStudentPswd(long studentId) {
		List<Student> students = studentResource.getStudents("select * from student where Id = " + studentId);
		updateStudentsPswd(students);
	}
	
	private void updateStudentsPswd(List<Student> students){
		String query = "update student set Password = ? where Id = ?";
		for(Student student: students) {
			try{
			    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setString(1, randomString(6));
		    	preparedStatement.setLong(2, student.getId());
		    	preparedStatement.executeUpdate();
			} catch(Exception e) {
			    e.printStackTrace();
			}
		}
	}
	
	public void updateTeacherPswd(long teacherId) {
		List<Teacher> teachers = teacherResource.getTeacherById(teacherId);
		updateTeacherPassword(teachers);
	}
	
	public void updateTeachersPswd(long schoolId) {
		List<Teacher> teachers = teacherResource.getTeacherList(schoolId);
		updateTeacherPassword(teachers);
	}
	
	private void updateTeacherPassword(List<Teacher> teachers) {
		String query = "update teacher set Password = ? where Id = ?";
		for(Teacher teacher: teachers) {
			try{
			    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setString(1, randomString(6));
		    	preparedStatement.setLong(2, teacher.getId());
		    	preparedStatement.executeUpdate();
			} catch(Exception e) {
			    e.printStackTrace();
			}
		}
	}
	
}
