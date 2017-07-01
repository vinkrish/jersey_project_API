package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aanglearning.model.entity.Student;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.resource.entity.StudentResource;
import com.aanglearning.resource.entity.TeacherResource;
import com.aanglearning.service.DatabaseUtil;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SMSService {
	AmazonSNSClient snsClient = new AmazonSNSClient();
	Map<String, MessageAttributeValue> smsAttributes = 
            new HashMap<String, MessageAttributeValue>();
	
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
	
	private void sendStudentsPswd(final List<Student> students){
		new Thread(new Runnable() {
			  @Override
			  public void run() {
				  for(Student student: students) {
						sendSMSMessage("Your password for Shikshitha Parent App is " + student.getPassword(), "+91" + student.getUsername());
					}
			  }
			}).start();
	}
	
	public void sendStudentUserPassword(String username) {
		List<Student> students = studentResource.getStudents("select * from student where Username = '" + username + "'");
		sendStudentsPswd(students);
	}
	
	public void sendTeacherPswd(long teacherId) {
		List<Teacher> teachers = teacherResource.getTeacherById(teacherId);
		sendTeacherPassword(teachers);
	}
	
	public void sendTeachersPswd(long schoolId) {
		List<Teacher> teachers = teacherResource.getTeacherList(schoolId);
		sendTeacherPassword(teachers);
	}
	
	private void sendTeacherPassword(final List<Teacher> teachers) {
		new Thread(new Runnable() {
			  @Override
			  public void run() {
				  for(Teacher teacher: teachers) {
						sendSMSMessage("Your password for Shikshitha Teacher App is " + teacher.getPassword(), "+91" + teacher.getUsername());
					}
			  }
			}).start();
	}
	
	public void sendTeacherUserPassword(String username) {
		Teacher teacher = teacherResource.getTeacher(username);
		sendTeacherPassword(Arrays.asList(teacher));
	}
	
	public void sendPrincipalUserPassword(final String username) {
		String query = "select AdminPassword from school where Mobile1 = ? or Mobile2 = ?" ;
		final String[] adminPassword = new String[1];
		adminPassword[0] = "";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, username);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				adminPassword[0] = rs.getString("AdminPassword");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			  @Override
			  public void run() {
				  sendSMSMessage("Your password for Shikshitha Principal App is " + adminPassword[0], "+91" +username);
			  }
			}).start();
	}
	
	public void sendSMSMessage(String message, String phoneNumber) {
		smsAttributes.put("AWS.SNS.SMS.SMSType", 
				new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));
		
        PublishResult result = snsClient.publish(new PublishRequest()
                        .withMessage(message)
                        .withPhoneNumber(phoneNumber)
                        .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID.
	}

}
