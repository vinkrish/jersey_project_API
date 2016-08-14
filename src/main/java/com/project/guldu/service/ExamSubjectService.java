package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.ExamSubject;

public class ExamSubjectService {
	Statement stmt = null;

	public ExamSubjectService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ExamSubject> getExamSubjects(long examId) {
		String query = "select * from exam_subject where ExamId = " + examId;
		List<ExamSubject> examSubjects = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				ExamSubject examSubject = new ExamSubject();
				examSubject.setId(rs.getLong("Id"));
				examSubject.setExamId(rs.getLong("ExamId"));
				examSubject.setSubjectId(rs.getLong("SubjectId"));
				examSubject.setMaximumMark(rs.getFloat("MaximumMark"));
				examSubject.setFailMark(rs.getFloat("FailMark"));
				examSubject.setPercentage(rs.getFloat("Percentage"));
				examSubjects.add(examSubject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examSubjects;
	}
	
	public ExamSubject add(ExamSubject examSubject) {
		try {
			String query = "insert into exam(Id, ExamName, ClassId, Term, Type, Percentage) "
					+ "values ("
					+ examSubject.getId() + "," 
					+ examSubject.getExamId() + ","
					+ examSubject.getSubjectId() + ","
					+ examSubject.getMaximumMark() + ","
					+ examSubject.getFailMark() + ","
					+ examSubject.getPercentage() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			examSubject.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examSubject;
	}
	
	public void update(ExamSubject examSubject) {
		try {
			String query = "update exam set MaximumMark="+examSubject.getMaximumMark()+ ", FailMark="+ examSubject.getFailMark()+ ", Percentage="+examSubject.getPercentage() 
			+ " where Id=" + examSubject.getId();
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from exam_subject where Id=" + id;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
