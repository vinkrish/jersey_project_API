package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.aanglearning.model.entity.Student;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.resource.entity.StudentResource;
import com.aanglearning.resource.entity.TeacherResource;
import com.aanglearning.service.DatabaseUtil;

public class SMSService {
	Connection connection;
	StudentResource studentResource = new StudentResource();
	TeacherResource teacherResource = new TeacherResource();
	
	public SMSService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendClassPswd(long classId) {
		List<Student> students = studentResource.getStudents("select * from student where ClassId = " + classId);
		sendStudentsPswd(students);
	}
	
	public void sendSectionPswd(long sectionId) {
		List<Student> students = studentResource.getStudents("select * from student where SectionId = " + sectionId);
		sendStudentsPswd(students);
	}
	
	public void sendStudentPswd(long studentId) {
		List<Student> students = studentResource.getStudents("select * from student where Id = " + studentId);
		sendStudentsPswd(students);
	}
	
	private void sendStudentsPswd(List<Student> students){
		for(Student student: students) {
			
		}
	}
	
	public void sendTeacherPswd(long teacherId) {
		List<Teacher> teachers = teacherResource.getTeacherById(teacherId);
		sendTeacherPassword(teachers);
	}
	
	public void sendTeachersPswd(long schoolId) {
		List<Teacher> teachers = teacherResource.getTeacherList(schoolId);
		sendTeacherPassword(teachers);
	}
	
	private void sendTeacherPassword(List<Teacher> teachers) {
		for(Teacher teacher: teachers) {
			
		}
	}

}
