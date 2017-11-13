package com.aanglearning.service.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.ExamSubject;
import com.aanglearning.service.DatabaseUtil;

public class ExamSubjectService {
	Connection connection;

	public ExamSubjectService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ExamSubject> getExamSubjects(long examId) {
		String query = "select * from exam_subject where ExamId = ? order by Orders";
		List<ExamSubject> examSubjects = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, examId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				ExamSubject examSubject = new ExamSubject();
				examSubject.setId(rs.getLong("Id"));
				examSubject.setExamId(rs.getLong("ExamId"));
				examSubject.setSubjectId(rs.getLong("SubjectId"));
				examSubject.setSubjectName(rs.getString("SubjectName"));
				examSubject.setType(rs.getString("Type"));
				examSubject.setMaximumMark(rs.getFloat("MaximumMark"));
				examSubject.setFailMark(rs.getFloat("FailMark"));
				examSubject.setCalculation(rs.getInt("Calculation"));
				examSubject.setPercentage(rs.getFloat("Percentage"));
				examSubject.setOrders(rs.getInt("Orders"));
				examSubjects.add(examSubject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examSubjects;
	}
	
	public ExamSubject add(ExamSubject examSubject) {
		String query = "insert into exam_subject(ExamId, SubjectId, SubjectName, Type, MaximumMark, FailMark, Calculation, Percentage, Orders) "
				+ "values (?,?,?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, examSubject.getExamId());
	    	preparedStatement.setLong(2, examSubject.getSubjectId());
	    	preparedStatement.setString(3, examSubject.getSubjectName());
	    	preparedStatement.setString(4, examSubject.getType());
	    	preparedStatement.setFloat(5, examSubject.getMaximumMark());
	    	preparedStatement.setFloat(6, examSubject.getFailMark());
	    	preparedStatement.setInt(7, examSubject.getCalculation());
	    	preparedStatement.setFloat(8, examSubject.getPercentage());
	    	preparedStatement.setInt(9, examSubject.getOrders());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			examSubject.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return examSubject;
	}
	
	public void update(ExamSubject examSubject) {
		String query = "update exam_subject set Type = ?, MaximumMark = ?, FailMark = ?, Calculation = ?, Percentage = ?, Orders = ? where Id = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, examSubject.getType());
	    	preparedStatement.setFloat(2, examSubject.getMaximumMark());
	    	preparedStatement.setFloat(3, examSubject.getFailMark());
	    	preparedStatement.setInt(4, examSubject.getCalculation());
	    	preparedStatement.setFloat(5, examSubject.getPercentage());	 
	    	preparedStatement.setInt(6, examSubject.getOrders());
	    	preparedStatement.setLong(7, examSubject.getId());	    
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void delete(long id) {
		String query = "delete from exam exam_subject Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
