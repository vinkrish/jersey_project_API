package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.aanglearning.model.entity.Attendance;
import com.aanglearning.model.entity.Student;
import com.aanglearning.resource.entity.StudentResource;
import com.aanglearning.service.JDBC;
import com.google.gson.Gson;

public class AttendanceService {
	Statement stmt = null;
	StudentResource studentResource;

	public AttendanceService() {
		try {
			stmt = JDBC.getConnection().createStatement();
			studentResource = new StudentResource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Attendance> dailyAttendanceMarked(long sectionId, String dateAttendance) {
		String query = "select * from attendance where SectionId = " + sectionId + " and DateAttendance = '"
				+ dateAttendance + "'";
		return getAttendanceList(query);
	}

	public List<Attendance> dailyAttendanceUnmarked(long sectionId, String dateAttendance) {
		List<Attendance> unMarkedAttendanceList = new ArrayList<>();
		String query = "select * from student " + "where Id not in "
				+ "(select StudentId from attendance where SectionId=" + sectionId + " and DateAttendance='"
				+ dateAttendance + "')" + "and SectionId = " + sectionId;
		List<Student> students = studentResource.getStudents(query);
		for (Student student : students) {
			Attendance att = new Attendance();
			att.setId(0);
			att.setSectionId(sectionId);
			att.setStudentId(student.getId());
			att.setStudentName(student.getStudentName());
			att.setSubjectId(0);
			att.setType("Daily");
			att.setSession(0);
			att.setDateAttendance(dateAttendance);
			att.setTypeOfLeave("");
			unMarkedAttendanceList.add(att);
		}
		return unMarkedAttendanceList;
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

	public List<Attendance> sessionAttendanceMarked(int session, long sectionId, String dateAttendance) {
		String query = "select * from attendance where Session = " + session + " and " + "SectionId = " + sectionId
				+ " and DateAttendance = '" + dateAttendance + "'";
		return getAttendanceList(query);
	}

	public List<Attendance> sessionAttendanceUnmarked(int session, long sectionId, String dateAttendance) {
		List<Attendance> unMarkedAttendanceList = new ArrayList<>();
		String query = "select * from student " + "where Id not in "
				+ "(select StudentId from attendance where SectionId=" + sectionId + " and Session=" + session
				+ " and DateAttendance='" + dateAttendance + "')" + "and SectionId = " + sectionId;
		List<Student> students = studentResource.getStudents(query);
		for (Student student : students) {
			Attendance att = new Attendance();
			att.setId(0);
			att.setSectionId(sectionId);
			att.setStudentId(student.getId());
			att.setStudentName(student.getStudentName());
			att.setSubjectId(0);
			att.setType("Daily");
			att.setSession(session);
			att.setDateAttendance(dateAttendance);
			att.setTypeOfLeave("");
			unMarkedAttendanceList.add(att);
		}
		return unMarkedAttendanceList;
	}

	public void addAttendance(String attendanceStr) {
		JSONArray attendanceArray = new JSONArray(attendanceStr);
		for (int i = 0; i < attendanceArray.length(); i++) {
			JSONObject attendanceJson = attendanceArray.getJSONObject(i);
			Gson gson = new Gson();
			Attendance attendance = gson.fromJson(attendanceJson.toString(), Attendance.class);
			try {
				String query = "insert into attendance(Id, SectionId, StudentId, StudentName, "
						+ "SubjectId, Type, Session, DateAttendance, TypeOfLeave) " + "values (" + attendance.getId()
						+ "," + attendance.getSectionId() + "," + attendance.getStudentId() + ",'"
						+ attendance.getStudentName() + "'," + attendance.getSubjectId() + ",'" + attendance.getType()
						+ "'," + attendance.getSession() + ",'" + attendance.getDateAttendance() + "','"
						+ attendance.getTypeOfLeave() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addAttendanceList(List<Attendance> attendances) {
		for (Attendance attendance : attendances) {
			try {
				String query = "insert into attendance(Id, SectionId, StudentId, StudentName, "
						+ "SubjectId, Type, Session, DateAttendance, TypeOfLeave) " + "values (" + attendance.getId()
						+ "," + attendance.getSectionId() + "," + attendance.getStudentId() + ",'"
						+ attendance.getStudentName() + "'," + attendance.getSubjectId() + ",'" + attendance.getType()
						+ "'," + attendance.getSession() + ",'" + attendance.getDateAttendance() + "','"
						+ attendance.getTypeOfLeave() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Attendance noAbsentees(Attendance attendance) {
		try {
			String query = "insert into attendance(Id, SectionId, Type, Session, DateAttendance, TypeOfLeave) "
					+ "values (" + attendance.getId() + "," + attendance.getSectionId() + ",'" + attendance.getType()
					+ "'," + attendance.getSession() + ",'" + attendance.getDateAttendance() + "','"
					+ attendance.getTypeOfLeave() + "')";
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				int idColVar = rs.getInt(1);
				attendance.setId(idColVar);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attendance;
	}

	public void update(Attendance attendance) {
		try {
			String query = "update attendance set Type= '" + attendance.getType() + "', Session="
					+ attendance.getSession() + ", TypeOfLeave = '" + attendance.getTypeOfLeave() + "' where Id="
					+ attendance.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long attendanceId) {
		try {
			String query = "delete from attendance where Id=" + attendanceId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteWhole(Attendance attendance) {
		try {
			String query = "delete from attendance where SectionId=" + attendance.getSectionId() + " and Session = "
					+ attendance.getSession() + " and DateAttendance = " + attendance.getDateAttendance();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
