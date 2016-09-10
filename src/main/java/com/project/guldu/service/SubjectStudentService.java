package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.project.guldu.model.SubjectStudent;

public class SubjectStudentService {
	Statement stmt = null;

	public SubjectStudentService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public SubjectStudent getSubjectStudents(long sectionId, long subjectId) {
		String query = "select * from subject_student where SectionId = " + sectionId + " and SubjectId = " + subjectId;
		SubjectStudent ss = new SubjectStudent();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				ss.setId(rs.getLong("Id"));
				ss.setSectionId(rs.getLong("SectionId"));
				ss.setSubjectId(rs.getLong("SubjectId"));
				ss.setStudentIds(rs.getString("StudentIds"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ss;
	}
	
	public SubjectStudent add(SubjectStudent ss) {
		try {
			String query = "insert into subject_student(Id, SectionId, SubjectId, StudentIds) "
					+ "values (" 
					+ ss.getId() + ","
					+ ss.getSectionId() + ","
					+ ss.getSubjectId() + ",'"
					+ ss.getStudentIds() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ss.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ss;
	}
	
	public void update(SubjectStudent ss) {
		try {
			String query = "update subject_student set StudentIds = '" + ss.getStudentIds()  
			+ "' where Id=" + ss.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
