package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.Attendance;

public class AttendanceService {
	Statement stmt = null;

	public AttendanceService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Attendance> getAttendanceToday(long schoolId, String dateAttendance) {
		String query = "select * from attendance where schoolId = " + schoolId + " and DateAttendance = " + dateAttendance;
		return getAttendanceList(query);
	}


	public List<Attendance> getAttendanceRange(long attendanceIndex) {
		String query = "select * from attendance LIMIT 100 OFFSET " + attendanceIndex;
		return getAttendanceList(query);
	}
	
	public List<Attendance> getAttendanceList(String query){
		List<Attendance> attList = new ArrayList<Attendance>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Attendance attendance = new Attendance();
				attendance.setId(rs.getLong("Id"));
				attendance.setSectionId(rs.getLong("SectionId"));
				attendance.setStudentId(rs.getLong("StudentId"));
				attendance.setSubjectId(rs.getLong("SubjectId"));
				attendance.setType(rs.getInt("Type"));
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

	public void addAttendance(String attendanceStr) {
		JSONArray attendanceArray = new JSONArray(attendanceStr);
		for (int i = 0; i < attendanceArray.length(); i++) {
			JSONObject attendanceJson = attendanceArray.getJSONObject(i);
			Gson gson = new Gson();
			Attendance attendance = gson.fromJson(attendanceJson.toString(), Attendance.class);
			try {
				String query = "insert into attendance(AttendanceId, SectionId, StudentId, SubjectId, Type, Session, DateAttendance, TypeOfLeave) "
						+ "values (" 
						+ attendance.getId() + "," 
						+ attendance.getSectionId() + "," 
						+ attendance.getStudentId() + "," 
						+ attendance.getSubjectId() + "," 
						+ attendance.getType() + "," 
						+ attendance.getSession() + ",'" 
						+ attendance.getDateAttendance() + "','" 
						+ attendance.getTypeOfLeave() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
