package com.aanglearning.service.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.ActivityScore;
import com.aanglearning.service.DatabaseUtil;

public class ActivityScoreService {
	Connection connection;

	public ActivityScoreService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<ActivityScore> getActivityScores(long activityId) {
		String query = "select * from activity_score where ActivityId=?";
		List<ActivityScore> scores = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, activityId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				ActivityScore score = new ActivityScore();
				score.setId(rs.getLong("Id"));
				score.setActivityId(rs.getLong("ActivityId"));
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
	
	public ActivityScore getActivityScore(long activityId, long studentId) {
		String query = "select * from activity_score where ActivityId=? and StudentId=?";
		ActivityScore score = new ActivityScore();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, activityId);
			preparedStatement.setLong(2, studentId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				score.setId(rs.getLong("Id"));
				score.setActivityId(rs.getLong("ActivityId"));
				score.setStudentId(rs.getLong("StudentId"));
				score.setMark(rs.getFloat("Mark"));
				score.setGrade(rs.getString("Grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return score;
	}

	public void add(List<ActivityScore> scores) {
		String query = "insert into activity_score(Id, ActivityId, StudentId, Mark, Grade) values (?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(ActivityScore score: scores) {
		    	preparedStatement.setLong(1, score.getId());
		    	preparedStatement.setLong(2, score.getActivityId());
		    	preparedStatement.setLong(3, score.getStudentId());
		    	preparedStatement.setFloat(4, score.getMark());
		    	preparedStatement.setString(5, score.getGrade());
		    	preparedStatement.executeUpdate();
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update(List<ActivityScore> scores) {
		String query = "update activity_score set Mark=?, Grade=? where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(ActivityScore score: scores) {
		    	preparedStatement.setFloat(1, score.getMark());
		    	preparedStatement.setString(2, score.getGrade());
		    	preparedStatement.setLong(3, score.getId());
		    	preparedStatement.executeUpdate();
		    }
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void delete(long activityId) {
		String query = "delete from activity_score where ActivityId=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, activityId);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
