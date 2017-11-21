package com.aanglearning.service.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.Activity;
import com.aanglearning.model.exam.ActivityScore;
import com.aanglearning.model.exam.StudentScore;
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
				score.setRollNo(rs.getInt("RollNo"));
				score.setStudentId(rs.getLong("StudentId"));
				score.setStudentName(rs.getString("StudentName"));
				score.setMark(rs.getFloat("Mark"));
				score.setGrade(rs.getString("Grade"));
				scores.add(score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scores;
	}
	
	public List<StudentScore> getStudentScore(long sectionId, long examId, long subjectId, long studentId) {
		List<Activity> activities = getActivities(sectionId, examId, subjectId);
		List<StudentScore> studentScores = new ArrayList<>();
		
		String query = "select * from activity_score where ActivityId=? and StudentId=?";
		for(Activity activity: activities) {
			StudentScore score = new StudentScore();
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setLong(1, activity.getId());
				preparedStatement.setLong(2, studentId);
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()){
					score.setSchId(activity.getId());
					score.setSchName(activity.getActivityName());
					score.setMark(rs.getFloat("Mark"));
					score.setGrade(rs.getString("Grade"));
					studentScores.add(score);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return studentScores;
	}
	
	private List<Activity> getActivities(long sectionId, long examId, long subjectId) {
		String query = "select * from activity where SectionId=? and ExamId=? and SubjectId=? order by Orders";
		List<Activity> activities = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, sectionId);
			preparedStatement.setLong(2, examId);
			preparedStatement.setLong(3, subjectId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Activity activity = new Activity();
				activity.setId(rs.getLong("Id"));
				activity.setActivityName(rs.getString("ActivityName"));
				activities.add(activity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activities;
	}

	public void add(List<ActivityScore> scores) {
		String query = "insert into activity_score(Id, ActivityId, RollNo, StudentId, StudentName, Mark, Grade) values (?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(ActivityScore score: scores) {
		    	preparedStatement.setLong(1, score.getId());
		    	preparedStatement.setLong(2, score.getActivityId());
		    	preparedStatement.setInt(3, score.getRollNo());
		    	preparedStatement.setLong(4, score.getStudentId());
		    	preparedStatement.setString(5, score.getStudentName());
		    	preparedStatement.setFloat(6, score.getMark());
		    	preparedStatement.setString(7, score.getGrade());
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
