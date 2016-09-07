package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.Activity;

public class ActivityService {
	Statement stmt = null;

	public ActivityService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Activity> getActivities(long sectionId, long examId, long subjectId) {
		String query = "select * from activity where SectionId = " + sectionId + " and ExamId = " + examId + " and SubjectId = " + subjectId;
		List<Activity> activities = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Activity activity = new Activity();
				activity.setId(rs.getLong("Id"));
				activity.setSectionId(rs.getLong("SectionId"));
				activity.setExamId(rs.getLong("ExamId"));
				activity.setSubjectId(rs.getLong("SubjectId"));
				activity.setActivityName(rs.getString("ActivityName"));
				activity.setMaximumMark(rs.getFloat("MaximumMark"));
				activity.setWeightage(rs.getFloat("Weightage"));
				activity.setCalculation(rs.getInt("Calculation"));
				activity.setActivityAvg(rs.getFloat("ActivityAvg"));
				activities.add(activity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activities;
	}
	
	public Activity add(Activity activity) {
		try {
			String query = "insert into activity(Id, SectionId, ExamId, SubjectId, ActivityName, MaximumMark, Weightage, Calculation, ActivityAvg) "
					+ "values ("
					+ activity.getId() + "," 
					+ activity.getSectionId() + ","
					+ activity.getExamId() + ","
					+ activity.getSubjectId() + ",'"
					+ activity.getActivityName() + "',"
					+ activity.getMaximumMark() + ","
					+ activity.getWeightage() + ","
					+ activity.getCalculation() + ","
					+ activity.getActivityAvg() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			activity.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activity;
	}
	
	public void update(Activity activity) {
		try {
			String query = "update activity set"
					+ " ActivityName = '" + activity.getActivityName()
					+ "', MaximumMark="+ activity.getMaximumMark() 
					+ ", Weightage="+ activity.getWeightage() 
					+ ", Calculation="+ activity.getCalculation()
					+ " where Id=" + activity.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from activity where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
