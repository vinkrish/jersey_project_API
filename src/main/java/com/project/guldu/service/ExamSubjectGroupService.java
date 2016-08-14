package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.ExamSubjectGroup;

public class ExamSubjectGroupService {
	Statement stmt = null;

	public ExamSubjectGroupService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ExamSubjectGroup> getExamSubjectGroups(long examId) {
		String query = "select * from exam_subject_group where ExamId = " + examId;
		List<ExamSubjectGroup> examSubjectGroups = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				ExamSubjectGroup esg = new ExamSubjectGroup();
				esg.setId(rs.getLong("Id"));
				esg.setExamId(rs.getLong("ExamId"));
				esg.setSubjectGroupId(rs.getLong("SubjectGroupId"));
				examSubjectGroups.add(esg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examSubjectGroups;
	}
	
	public ExamSubjectGroup add(ExamSubjectGroup esg) {
		try {
			String query = "insert into exam_subject_group(Id, ExamId, SubjectGroupId) "
					+ "values ("
					+ esg.getId() + "," 
					+ esg.getExamId() + ","
					+ esg.getSubjectGroupId() + "')"; 
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			esg.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return esg;
	}
	
	public void delete(long id){
		try {
			String query = "delete from exam_subject_group where Id=" + id;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
