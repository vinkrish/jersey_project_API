package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.SubjectGroupSubject;
import com.aanglearning.model.entity.SubjectStudent;
import com.aanglearning.service.DatabaseUtil;

public class SubjectStudentService {
	Statement stmt;
	SubjectGroupSubjectService sgsService = new SubjectGroupSubjectService();

	public SubjectStudentService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
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

	public List<SubjectStudent> getSubjectStudentsList(long sectionId, long subjectGroupId) {
		List<SubjectStudent> ssList = new ArrayList<>();
		List<SubjectGroupSubject> sgsList = sgsService.getSubjectGroupSubjects(subjectGroupId);
		for(SubjectGroupSubject sgs: sgsList) {
			String query = "select * from subject_student "
					+ "where SectionId = " + sectionId + " and SubjectId = " + sgs.getSubjectId();
			try {
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()){
					SubjectStudent ss = new SubjectStudent();
					ss.setId(rs.getLong("Id"));
					ss.setSectionId(rs.getLong("SectionId"));
					ss.setSubjectId(rs.getLong("SubjectId"));
					ss.setStudentIds(rs.getString("StudentIds"));
					ssList.add(ss);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ssList;
	}

	public void add(List<SubjectStudent> ssList) {
		for (SubjectStudent ss: ssList) {
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
		}
	}

	public void update(List<SubjectStudent> ssList) {
		for(SubjectStudent ss: ssList) {
			try {
				String query = "update subject_student set StudentIds = '" + ss.getStudentIds()  
				+ "' where Id=" + ss.getId();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void recent(List<SubjectStudent> ssList) {
		try{
			for (SubjectStudent ss: ssList) {
				SubjectStudent temp = getSubjectStudents(ss.getSectionId(), ss.getSubjectId());
				if(temp.getStudentIds() == null) {
					String query = "insert into subject_student(Id, SectionId, SubjectId, StudentIds) "
							+ "values (" 
							+ ss.getId() + ","
							+ ss.getSectionId() + ","
							+ ss.getSubjectId() + ",'"
							+ ss.getStudentIds() + "')";
					stmt.executeUpdate(query);
				} else {
					String query = "update subject_student set StudentIds = '" + ss.getStudentIds()  
					+ "' where SectionId=" + ss.getSectionId() + " and SubjectId = "  + ss.getSubjectId();
					stmt.executeUpdate(query);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
