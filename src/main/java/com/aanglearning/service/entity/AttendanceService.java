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
import com.aanglearning.service.DatabaseUtil;
import com.google.gson.Gson;

public class AttendanceService {
	Statement stmt;
	StudentResource studentResource = new StudentResource();

	public AttendanceService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
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
			att.setStudentName(student.getName());
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
		String attendanceType = getClassAttendanceType(sectionId);
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
			att.setStudentName(student.getName());
			att.setSubjectId(0);
			att.setType(attendanceType);
			att.setSession(session);
			att.setDateAttendance(dateAttendance);
			att.setTypeOfLeave("");
			unMarkedAttendanceList.add(att);
		}
		return unMarkedAttendanceList;
	}
	
	public String getClassAttendanceType(long sectionId) {
		String query = "select A.AttendanceType from class A, section B where A.Id = B.ClassId and B.Id = " +  sectionId;
		String attendanceType = "Daily";
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				attendanceType = rs.getString("AttendanceType");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attendanceType;
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
		clearNoAbsentees(attendances.get(0));
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
	
	private void clearNoAbsentees(Attendance attendance) {
		try {
			String query = "delete from attendance where StudentId = 0 and SectionId=" + attendance.getSectionId() + " and Session=" + attendance.getSession() + " and DateAttendance='" + attendance.getDateAttendance()+ "'";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
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
