package com.aanglearning.service.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.AttendanceSet;
import com.aanglearning.model.entity.Attendance;
import com.aanglearning.resource.entity.StudentResource;
import com.aanglearning.service.JDBC;

public class AppAttendanceService {
	Statement stmt = null;
	StudentResource studentResource;

	public AppAttendanceService() {
		try {
			stmt = JDBC.getConnection().createStatement();
			studentResource = new StudentResource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	public void saveAttendance(List<Attendance> attendanceList) {
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

}
