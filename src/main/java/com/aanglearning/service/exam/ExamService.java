package com.aanglearning.service.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.Exam;
import com.aanglearning.service.DatabaseUtil;

public class ExamService {
	Connection connection;

	public ExamService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Exam> getExamList(long classId) {
		String query = "select * from exam where ClassId = ?";
		List<Exam> examList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, classId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Exam exam = new Exam();
				exam.setId(rs.getLong("Id"));
				exam.setExamName(rs.getString("ExamName"));
				exam.setClassId(rs.getLong("ClassId"));
				exam.setTerm(rs.getInt("Term"));
				exam.setType(rs.getString("Type"));
				exam.setCalculation(rs.getInt("Calculation"));
				exam.setPercentage(rs.getFloat("Percentage"));
				examList.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examList;
	}
	
	public Exam add(Exam exam) {
		String query = "insert into exam(ExamName, ClassId, Term, Type, Calculation, Percentage) "
				+ "values (?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, exam.getExamName());
	    	preparedStatement.setLong(2, exam.getClassId());
	    	preparedStatement.setInt(3, exam.getTerm());
	    	preparedStatement.setString(4, exam.getType());
	    	preparedStatement.setInt(5, exam.getCalculation());
	    	preparedStatement.setFloat(6, exam.getPercentage());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			exam.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return exam;
	}
	
	public void update(Exam exam) {
		String query = "update exam set ExamName = ?, Type = ?, Calculation = ?, Percentage = ? where Id = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, exam.getExamName());
	    	preparedStatement.setString(2, exam.getType());
	    	preparedStatement.setInt(3, exam.getCalculation());
	    	preparedStatement.setFloat(4, exam.getPercentage());
	    	preparedStatement.setLong(5, exam.getId());	    	
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void delete(long id) {
		String query = "delete from exam where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
