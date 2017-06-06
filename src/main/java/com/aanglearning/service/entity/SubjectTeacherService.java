package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.SubjectTeacher;
import com.aanglearning.service.DatabaseUtil;

public class SubjectTeacherService {
	Statement stmt;

	public SubjectTeacherService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<SubjectTeacher> getSubjectTeacher(long sectionId) {
		String query = "select * from subject_teacher where SectionId = " + sectionId;
		return getSubjectTeacherList(query);
	}
	
	public List<SubjectTeacher> getSubjectTeacherList(String query){
		List<SubjectTeacher> classList = new ArrayList<SubjectTeacher>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				SubjectTeacher subjectTeacher = new SubjectTeacher();
				subjectTeacher.setId(rs.getLong("Id"));
				subjectTeacher.setSectionId(rs.getLong("SectionId"));
				subjectTeacher.setSubjectId(rs.getLong("SubjectId"));
				subjectTeacher.setSubjectName(rs.getString("SubjectName"));
				subjectTeacher.setTeacherId(rs.getLong("TeacherId"));
				subjectTeacher.setTeacherName(rs.getString("TeacherName"));
				classList.add(subjectTeacher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classList;
	}
	
	public SubjectTeacher add(SubjectTeacher subjectTeacher) {
		try {
			String query = "insert into subject_teacher(Id, SectionId, SubjectId, TeacherId) "
					+ "values (" 
					+ subjectTeacher.getId() + "," 
					+ subjectTeacher.getSectionId() + ","
					+ subjectTeacher.getSubjectId() + ","
					+ subjectTeacher.getTeacherId() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			subjectTeacher.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectTeacher;
	}
	
	public void update(SubjectTeacher subjectTeacher) {
		try {
			String query = "update subject_teacher set SectionId = " + subjectTeacher.getSectionId() 
			+ ", TeacherId = " + subjectTeacher.getTeacherId() 
			+ ", TeacherName = '" + subjectTeacher.getTeacherName()
			+ "', SubjectId = " + subjectTeacher.getSubjectId() 
			+ ", SubjectName = '" + subjectTeacher.getSubjectName()
			+ "' where Id=" + subjectTeacher.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from subject_teacher where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
