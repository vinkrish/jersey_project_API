package com.aanglearning.service.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.SliptestScore;
import com.aanglearning.service.JDBC;

public class SliptestScoreService {
	Connection connection = null;

	public SliptestScoreService() {
		connection = JDBC.getConnection();
	}

	public List<SliptestScore> getSliptestScores(long sliptestId) {
		String query = "select * from sliptest_score where SliptestId=?";
		List<SliptestScore> scores = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, sliptestId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				SliptestScore score = new SliptestScore();
				score.setId(rs.getLong("Id"));
				score.setSliptestId(rs.getLong("sliptestId"));
				score.setStudentId(rs.getLong("StudentId"));
				score.setMark(rs.getFloat("Mark"));
				score.setGrade(rs.getString("Grade"));
				scores.add(score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scores;
	}

	public void add(List<SliptestScore> scores) {
		String query = "insert into sliptest_score(Id, SliptestId, StudentId, Mark, Grade) values (?,?,?,?,?)";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(SliptestScore score: scores) {
		    	preparedStatement.setLong(1, score.getId());
		    	preparedStatement.setLong(2, score.getSliptestId());
		    	preparedStatement.setLong(3, score.getStudentId());
		    	preparedStatement.setFloat(4, score.getMark());
		    	preparedStatement.setString(5, score.getGrade());
		    	preparedStatement.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void update(List<SliptestScore> scores) {
		String query = "update sliptest_score set Mark=?, Grade=? where Id=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(SliptestScore score: scores) {
		    	preparedStatement.setFloat(1, score.getMark());
		    	preparedStatement.setString(2, score.getGrade());
		    	preparedStatement.setLong(3, score.getId());
		    	preparedStatement.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void delete(long sliptestId) {
		String query = "delete from sliptest_score where SliptestId=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, sliptestId);
		    	preparedStatement.executeUpdate();
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
