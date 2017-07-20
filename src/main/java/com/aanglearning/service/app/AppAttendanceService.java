package com.aanglearning.service.app;

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

import com.aanglearning.model.app.AttendanceSet;
import com.aanglearning.model.entity.Attendance;
import com.aanglearning.resource.entity.StudentResource;
import com.aanglearning.service.DatabaseUtil;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;

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
			try {
				ResultSet rs = stmt.executeQuery("select Name, Mobile1 from student where Id = " + attendance.getStudentId() + " and IsLogged = 0");
				while (rs.next()){
					StringBuilder sb = new StringBuilder(rs.getString("Name")).append(" was absent on ").append(getDisplayFormattedDate(attendance.getDateAttendance()));
					if (attendance.getType().equals("Daily")) {
						sendSMSMessage(snsClient, smsAttributes, sb.toString(), rs.getString("Mobile1"));
					} else if (attendance.getType().equals("Session")) {
						String session = attendance.getSession() == 0 ? "morning" : "afternoon";
						sendSMSMessage(snsClient, smsAttributes, sb.append(" during ").append(session).append(" session").toString(), rs.getString("Mobile1"));
					} else if (attendance.getType().equals("Period")) {
						sendSMSMessage(snsClient, smsAttributes, sb.append(" during ").append(attendance.getSession()).append(" period").toString(), rs.getString("Mobile1"));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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
