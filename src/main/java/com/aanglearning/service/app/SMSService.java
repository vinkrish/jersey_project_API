package com.aanglearning.service.app;

import java.io.IOException;
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

import org.json.JSONArray;
import org.json.JSONObject;

import com.aanglearning.model.entity.Section;
import com.aanglearning.model.entity.Student;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.service.DatabaseUtil;
import com.aanglearning.service.FCMPost;
import com.aanglearning.service.entity.TeacherService;
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
	TeacherService teacherService = new TeacherService();
	
	public SMSService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendSchoolPswd(long schoolId) {
		List<Student> students = getStudents("select Username, Password from student where SchoolId = " + schoolId + " and Username != ' '");
		sendStudentsPswd(students);
	}
	
	public void sendLoggedOutStudentsPswd(long schoolId) {
		List<Student> students = getStudents("select Username, Password from student where SchoolId = " + schoolId + " and IsLogged = 0 and Username != ' '");
		sendStudentsPswd(students);
	}
	
	public void sendClassPswd(long classId) {
		List<Student> students = getStudents("select Username, Password from student where ClassId = " + classId + " and Username != ' '");
		sendStudentsPswd(students);
	}
	
	public void sendLoggedOutClassPswd(long classId) {
		List<Student> students = getStudents("select Username, Password from student where ClassId = " + classId + " and IsLogged = 0 and Username != ' '");
		sendStudentsPswd(students);
	}
	
	public void sendSectionPswd(long sectionId) {
		List<Student> students = getStudents("select Username, Password from student where SectionId = " + sectionId + " and Username != ' '");
		sendStudentsPswd(students);
	}
	
	public void sendStudentPswd(long studentId) {
		List<Student> students = getStudents("select Username, Password from student where Id = " + studentId + " and Username != ' '");
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
						sendSMSMessage("Your password for Shikshitha Parent App is " + student.getPassword() 
						+ "\nDownload: https://play.google.com/store/apps/developer?id=Shikshitha+Apps", "+91" + student.getUsername());
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
		List<Teacher> teachers = teacherService.getTeacherById(teacherId);
		sendTeacherPassword(teachers);
	}
	
	public void sendTeachersPswd(long schoolId) {
		List<Teacher> teachers = teacherService.getSchoolTeachers(schoolId);
		sendTeacherPassword(teachers);
	}
	
	public void sendLoggedOutTeachersPswd(long schoolId) {
		List<Teacher> teachers = teacherService.getTeacherList("select * from teacher where Username not in "
				+ "(select User from authorization where User in (select Username from teacher where SchoolId = " + schoolId 
				+ ")) and SchoolId = " + schoolId);
		sendTeacherPassword(teachers);
	}
	
	private void sendTeacherPassword(final List<Teacher> teachers) {
		new Thread(new Runnable() {
			  @Override
			  public void run() {
				  for(Teacher teacher: teachers) {
						sendSMSMessage("Your password for Shikshitha Teacher App is " + teacher.getPassword() 
						+ "\nDownload: https://play.google.com/store/apps/developer?id=Shikshitha+Apps", "+91" + teacher.getUsername());
					}
			  }
			}).start();
	}
	
	public void sendTeacherUserPassword(String username) {
		Teacher teacher = teacherService.getTeacher(username);
		sendTeacherPassword(Arrays.asList(teacher));
	}
	
	public void sendPrincipalUserPassword(final String username) {
		String query = "select t.Password from teacher t, school s where "
				+ "(s.Mobile1=? and t.Username=?) or (s.Mobile2=? and t.Username=?)" ;
		final String[] adminPassword = new String[1];
		adminPassword[0] = "";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, username);
			preparedStatement.setString(4, username);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				adminPassword[0] = rs.getString("Password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			  @Override
			  public void run() {
				  sendSMSMessage("Your password for Shikshitha Principal App is " + adminPassword[0] 
						  + "\nDownload: https://play.google.com/store/apps/developer?id=Shikshitha+Apps", "+91" +username);
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
        //System.out.println(result);
	}
	
	public String createSNSTopic(AmazonSNSClient snsClient) {
	    CreateTopicRequest createTopic = new CreateTopicRequest(System.currentTimeMillis() + "");
	    CreateTopicResult result = snsClient.createTopic(createTopic);
	    //System.out.println("Create topic request: " +  snsClient.getCachedResponseMetadata(createTopic));
	    //System.out.println("Create topic result: " + result);
	    return result.getTopicArn();
	}
	
	public void sendSchoolHomework(final long schoolId, final String homeworkDate) {
		
		new Thread(new Runnable() {
			  @Override
			  public void run() {
				  String formattedDate = getDisplayFormattedDate(homeworkDate);
					StringBuilder homeworkMessage = new StringBuilder("Homework : on ("+formattedDate+") \n");
					
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
						String topicArn = createSNSTopic(snsClient);
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
							ResultSet rs = stmt.executeQuery("select Name, Username from student where SectionId = " + section.getId() + " and IsLogged = 0 and Username != ' '");
							while (rs.next()){
								snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + rs.getString("Username")));
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
							
							JSONObject msg = new JSONObject();
							msg.put("section_id", section.getId());
							msg.put("date", formattedDate);
							msg.put("type", "homework");
							
							JSONObject fcm = new JSONObject();
						    fcm.put("registration_ids", getParentFCMToken(section.getId()));
						    fcm.put("data", msg);
						    fcm.put("time_to_live", 22200);
						    FCMPost fcmPost = new FCMPost();
						    try {
								fcmPost.post(fcm.toString(), "AAAANtOFq98:APA91bGLAt-wCJDBhzomz_GmlVW8TXyshKdR6NOzuKTOk0NgM29Ww7-tZzjxCjT0siEua6AQY7stUxRTnkf_8cD5QgypjWfOTn1UYnzOQOP6uAB7bR_SA0SkSlOmPi9gPp6iHJL4xAzw");
							} catch (IOException e) {
								e.printStackTrace();
							}
					    	break;
						}
						
					}
			  }
			}).start();
		
	}
	
	private JSONArray getParentFCMToken(long sectionId) {
		JSONArray fcmTokenArray = new JSONArray();
		String query = "select FcmToken from authorization where User in (select Username from student where SectionId = " + sectionId + ")";
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			if (rs.next()) {
				fcmTokenArray.put(rs.getString("FcmToken"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fcmTokenArray;
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
