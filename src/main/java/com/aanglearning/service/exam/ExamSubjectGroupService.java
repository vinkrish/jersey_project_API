package com.aanglearning.service.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.ExamSubjectGroup;
import com.aanglearning.service.DatabaseUtil;

public class ExamSubjectGroupService {
	Connection connection;

	public ExamSubjectGroupService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ExamSubjectGroup> getExamSubjectGroups(long examId) {
		String query = "select * from exam_subject_group where ExamId = ?";
		List<ExamSubjectGroup> examSubjectGroups = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, examId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				ExamSubjectGroup esg = new ExamSubjectGroup();
				esg.setId(rs.getLong("Id"));
				esg.setExamId(rs.getLong("ExamId"));
				esg.setSubjectGroupId(rs.getLong("SubjectGroupId"));
				esg.setSubjectGroupName(rs.getString("SubjectGroupName"));
				examSubjectGroups.add(esg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examSubjectGroups;
	}
	
	public ExamSubjectGroup add(ExamSubjectGroup esg) {
		String query = "insert into exam_subject_group(ExamId, SubjectGroupId, SubjectGroupName) values (?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, esg.getExamId());
	    	preparedStatement.setLong(2, esg.getSubjectGroupId());
	    	preparedStatement.setString(3, esg.getSubjectGroupName());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			esg.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		
		return esg;
	}
	
	public void delete(long id) {
		String query = "delete from exam_subject_group where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
