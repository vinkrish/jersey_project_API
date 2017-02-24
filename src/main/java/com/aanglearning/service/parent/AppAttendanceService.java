package com.aanglearning.service.parent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public List<Attendance> getAttendanceMarked(long sectionId, long studentId, String dateAttendance) {
		String query = "select * from attendance where SectionId = " + sectionId + " and DateAttendance > '"
				+ dateAttendance + "' and StudentId = " + studentId;
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

}
