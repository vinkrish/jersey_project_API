package com.project.guldu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.SubActivityScore;

public class SubActivityScoreService {
	Connection connection = null;

	public SubActivityScoreService() {
		connection = JDBC.getConnection();
	}

	public List<SubActivityScore> getSubActivityScores(long subActivityId) {
		String query = "select * from subactivity_score where SubActivityId=?";
		List<SubActivityScore> scores = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, subActivityId);
			ResultSet rs = preparedStatement.executeQuery(query);
			while (rs.next()){
				SubActivityScore score = new SubActivityScore();
				score.setId(rs.getLong("Id"));
				score.setSubActivityId(rs.getLong("SubActivityId"));
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

	public void add(List<SubActivityScore> scores) {
		String query = "insert into subactivity_score(Id, SubActivityId, StudentId, Mark, Grade) values (?,?,?,?,?)";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(SubActivityScore score: scores) {
		    	preparedStatement.setLong(1, score.getId());
		    	preparedStatement.setLong(2, score.getSubActivityId());
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
		} finally {
		    if(connection != null) {
		        try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
		}
	}

	public void update(List<SubActivityScore> scores) {
		String query = "update subactivity_score set Mark=?, Grade=? where Id=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(SubActivityScore score: scores) {
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
		} finally {
		    if(connection != null) {
		        try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
		}
	}
	
	public void delete(long subActivityId) {
		String query = "delete from subactivity_score where SubActivityId=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, subActivityId);
		    	preparedStatement.executeUpdate();
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
		    if(connection != null) {
		        try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
		}
	}

}
