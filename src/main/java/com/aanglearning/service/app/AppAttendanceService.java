package com.aanglearning.service.app;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

import com.aanglearning.model.app.AttendanceSet;
import com.aanglearning.model.entity.Attendance;
import com.aanglearning.resource.entity.StudentResource;
import com.aanglearning.service.DatabaseUtil;
import com.aanglearning.service.FCMPost;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class AppAttendanceService {
	Statement stmt;
	StudentResource studentResource;

	public AppAttendanceService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
			studentResource = new StudentResource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Attendance> getStudentAbsentDays(long studentId) {
		String query = "select * from attendance where StudentId = " + studentId;
		return getAttendanceList(query);
	}
	
	public List<Attendance> getAttendance(long sectionId) {
		String query = "select * from attendance where SectionId = " + sectionId;
		return getAttendanceList(query);
	}

	public List<Attendance> getAttendance(long sectionId, String dateAttendance) {
		String query = "select * from attendance where SectionId = " + sectionId + " and DateAttendance > '"
				+ dateAttendance + "'";
		return getAttendanceList(query);
	}
	
	public List<Attendance> getTodaysAttendance(long sectionId, String dateAttendance) {
		String query = "select * from attendance where SectionId = " + sectionId + " and DateAttendance = '"
				+ dateAttendance + "' order by Session ASC";
		return getAttendanceList(query);
	}
	
	public AttendanceSet getAttendanceSet(long sectionId, String dateAttendance, int session) {
		AttendanceSet attendanceSet = new AttendanceSet();
		attendanceSet.setAttendanceList(getAttendanceList("select * from attendance where SectionId = " + sectionId + " and DateAttendance = '"
				+ dateAttendance + "' and Session = " + session));
		attendanceSet.setStudents(studentResource.getStudents("select * from student where Id not in "
				+ "(select StudentId from attendance where "
				+ "SectionId = " + sectionId + " and DateAttendance = '" + dateAttendance + "' and Session = " + session 
				+ ") and SectionId = " + sectionId + " order by RollNo"));
		return attendanceSet;
	}
	
	public List<Attendance> getAttendanceList(String query) {
		List<Attendance> attList = new ArrayList<Attendance>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Attendance attendance = new Attendance();
				attendance.setId(rs.getLong("Id"));
				attendance.setSectionId(rs.getLong("SectionId"));
				attendance.setStudentId(rs.getLong("StudentId"));
				attendance.setStudentName(rs.getString("StudentName"));
				attendance.setSubjectId(rs.getLong("SubjectId"));
				attendance.setType(rs.getString("Type"));
				attendance.setSession(rs.getInt("Session"));
				attendance.setDateAttendance(rs.getString("DateAttendance"));
				attendance.setTypeOfLeave(rs.getString("TypeOfLeave"));
				attList.add(attendance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attList;
	}
	
	public void saveAttendance(final List<Attendance> attendanceList) {
		clearNoAbsentees(attendanceList.get(0));
		try {
			for(Attendance attendance: attendanceList) {
				String query = "insert into attendance(Id, SectionId, StudentId, StudentName, "
						+ "SubjectId, Type, Session, DateAttendance, TypeOfLeave) " + "values (" + attendance.getId()
						+ "," + attendance.getSectionId() + "," + attendance.getStudentId() + ",'"
						+ attendance.getStudentName() + "'," + attendance.getSubjectId() + ",'" + attendance.getType()
						+ "'," + attendance.getSession() + ",'" + attendance.getDateAttendance() + "','"
						+ attendance.getTypeOfLeave() + "')";
				stmt.executeUpdate(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {
			  @Override
			  public void run() {
				  sendAttendanceSMS(attendanceList);
			  }
			}).start();
	}
	
	private void clearNoAbsentees(Attendance attendance) {
		try {
			String query = "delete from attendance where StudentId = 0 and SectionId=" + attendance.getSectionId() + " and Session=" + attendance.getSession() + " and DateAttendance='" + attendance.getDateAttendance()+ "'";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAttendance(List<Attendance> attendanceList) {
		try {
			for(Attendance attendance: attendanceList) {
				String query = "delete from attendance where Id=" + attendance.getId();
				stmt.executeUpdate(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendAttendanceSMS(List<Attendance> attendanceList) {
		AmazonSNSClient snsClient = new AmazonSNSClient();
		Map<String, MessageAttributeValue> smsAttributes = 
	            new HashMap<String, MessageAttributeValue>();
		
		for(Attendance attendance: attendanceList) {
			String session = " ";
			try {
				ResultSet rs = stmt.executeQuery("select Name, Username from student where Id = " + attendance.getStudentId() + " and IsLogged = 0");
				while (rs.next()){
					StringBuilder sb = new StringBuilder(rs.getString("Name")).append(" was absent on ").append(getDisplayFormattedDate(attendance.getDateAttendance()));
					if (attendance.getType().equals("Daily")) {
						sendSMSMessage(snsClient, smsAttributes, sb.toString(), rs.getString("Username"));
					} else if (attendance.getType().equals("Session")) {
						session = attendance.getSession() == 0 ? "morning" : "afternoon";
						sendSMSMessage(snsClient, smsAttributes, sb.append(" during ").append(session).append(" session").toString(), rs.getString("Username"));
					} else if (attendance.getType().equals("Period")) {
						session = String.valueOf(attendance.getSession());
						sendSMSMessage(snsClient, smsAttributes, sb.append(" during ").append(session).append(" period").toString(), rs.getString("Username"));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String username = "";
			String query_search = "select Username from student where Id = " + attendance.getStudentId() + " and IsLogged=1 and Username != ''";
			try {
				ResultSet resultSet = stmt.executeQuery(query_search);
				if (resultSet.next()) {
					username = resultSet.getString("Username");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(!username.equals("")) {
				session = "";
				if (attendance.getType().equals("Session")) {
					session = attendance.getSession() == 0 ? "morning" : "afternoon";
				} else if (attendance.getType().equals("Period")) {
					session = String.valueOf(attendance.getSession());
				}
				
				JSONObject msg = new JSONObject();
				msg.put("student_id", attendance.getStudentId());
				msg.put("date", getDisplayFormattedDate(attendance.getDateAttendance()));
				msg.put("attendance_type", attendance.getType());
				msg.put("session", session);
				msg.put("type", "attendance");
				
				JSONObject fcm = new JSONObject();
			    fcm.put("to", getFCMToken(username));
			    fcm.put("data", msg);
			    fcm.put("time_to_live", 22200);
			    FCMPost fcmPost = new FCMPost();
			    try {
					fcmPost.post(fcm.toString(), "AAAANtOFq98:APA91bGLAt-wCJDBhzomz_GmlVW8TXyshKdR6NOzuKTOk0NgM29Ww7-tZzjxCjT0siEua6AQY7stUxRTnkf_8cD5QgypjWfOTn1UYnzOQOP6uAB7bR_SA0SkSlOmPi9gPp6iHJL4xAzw");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	private String getFCMToken(String user) {
		String fcmToken = "";
		String query = "select FcmToken from authorization where User = '" + user + "'";
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				fcmToken = rs.getString("FcmToken");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fcmToken;
	}
	
	public void sendSMSMessage(AmazonSNSClient snsClient, Map<String, MessageAttributeValue> smsAttributes, String message, String phoneNumber) {
		smsAttributes.put("AWS.SNS.SMS.SMSType", 
				new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));
		
        PublishResult result = snsClient.publish(new PublishRequest()
                        .withMessage(message)
                        .withPhoneNumber("91"+phoneNumber)
                        .withMessageAttributes(smsAttributes));
        System.out.println(result);
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
