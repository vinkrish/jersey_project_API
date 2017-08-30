package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.aanglearning.model.entity.Section;
import com.aanglearning.model.entity.Student;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.resource.entity.TeacherResource;
import com.aanglearning.service.DatabaseUtil;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SMSService {
	AmazonSNSClient snsClient = new AmazonSNSClient();
	
	Map<String, MessageAttributeValue> smsAttributes = 
            new HashMap<String, MessageAttributeValue>();
	
	Connection connection;
	Statement stmt;
	TeacherResource teacherResource = new TeacherResource();
	
	public SMSService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendSchoolPswd(long schoolId) {
		List<Student> students = getStudents("select Username, Password from student where SchoolId = " + schoolId);
		sendStudentsPswd(students);
	}
	
	public void sendClassPswd(long classId) {
		List<Student> students = getStudents("select Username, Password from student where ClassId = " + classId);
		sendStudentsPswd(students);
	}
	
	public void sendSectionPswd(long sectionId) {
		List<Student> students = getStudents("select Username, Password from student where SectionId = " + sectionId);
		sendStudentsPswd(students);
	}
	
	public void sendStudentPswd(long studentId) {
		List<Student> students = getStudents("select Username, Password from student where Id = " + studentId);
		sendStudentsPswd(students);
	}
	
	public void sendStudentUserPassword(String username) {
		List<Student> students = getStudents("select Username, Password from student where Username = '" + username + "'");
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
	
	private List<Student> getStudents(String query) {
		List<Student> studentList = new ArrayList<Student>();
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Student student = new Student();
				student.setUsername(rs.getString("Username"));
				student.setPassword(rs.getString("Password"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
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
        System.out.println(result);
	}
	
	public String createSNSTopic(AmazonSNSClient snsClient) {
	    CreateTopicRequest createTopic = new CreateTopicRequest("shikshithaTopic");
	    CreateTopicResult result = snsClient.createTopic(createTopic);
	    System.out.println("Create topic request: " + 
	        snsClient.getCachedResponseMetadata(createTopic));
	    System.out.println("Create topic result: " + result);
	    return result.getTopicArn();
	}
	
	public void sendSchoolHomework(final long schoolId, final String homeworkDate) {
		
		new Thread(new Runnable() {
			  @Override
			  public void run() {
				  String topicArn = createSNSTopic(snsClient);
					StringBuilder homeworkMessage = new StringBuilder("Homework : on ("+getDisplayFormattedDate(homeworkDate)+") \n");
					
					try {
						stmt = connection.createStatement();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					List<Section> sectionList = new ArrayList<Section>();
					try {
						ResultSet rs = stmt.executeQuery("select Id from section where ClassId in (select Id from class where SchoolId = " + schoolId + ")");
						while (rs.next()){
							Section section = new Section();
							section.setId(rs.getLong("Id"));
							sectionList.add(section);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					for(Section section: sectionList) {
						boolean isHomework = false;
						StringBuilder sectionHomework = new StringBuilder(homeworkMessage);
						try {
							ResultSet rs = stmt.executeQuery("select * from homework where SectionId = " + section.getId() + " and HomeworkDate = '" + homeworkDate + "'");
							while (rs.next()) {
								isHomework = true;
								sectionHomework.append(rs.getString("SubjectName") + " - " + rs.getString("HomeworkMessage")).append("\n");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						try {
							ResultSet rs = stmt.executeQuery("select Name, Mobile1 from student where SectionId = " + section.getId() + " and IsLogged = 0 and Mobile1 != ''");
							while (rs.next()){
								snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + rs.getString("Mobile1")));
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						if(isHomework) {
							smsAttributes.put("AWS.SNS.SMS.SMSType", 
									new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));
							
							snsClient.publish(new PublishRequest()
				                    .withTopicArn(topicArn)
				                    .withMessage(sectionHomework.toString())
				                    .withMessageAttributes(smsAttributes));
						}
						
					}
			  }
			}).start();
		
	}
	
	public String getDisplayFormattedDate(String dateString) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
    }

}
