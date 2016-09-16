package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.SubActivity;

public class SubActivityService {
	Statement stmt = null;

	public SubActivityService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<SubActivity> getSubActivities(long activityId) {
		String query = "select * from subactivity where ActivityId = " + activityId + " order by Orders";
		List<SubActivity> subactivities = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				SubActivity subactivity = new SubActivity();
				subactivity.setId(rs.getLong("Id"));
				subactivity.setActivityId(rs.getLong("ActivityId"));
				subactivity.setSubActivityName(rs.getString("SubActivityName"));
				subactivity.setType(rs.getString("Type"));
				subactivity.setMaximumMark(rs.getFloat("MaximumMark"));
				subactivity.setWeightage(rs.getFloat("Weightage"));
				subactivity.setCalculation(rs.getInt("Calculation"));
				subactivity.setSubActivityAvg(rs.getFloat("SubActivityAvg"));
				subactivity.setOrders(rs.getInt("Orders"));
				subactivities.add(subactivity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subactivities;
	}
	
	public SubActivity add(SubActivity subactivity) {
		try {
			String query = "insert into subactivity(Id, ActivityId, SubActivityName, Type, MaximumMark, Weightage, Calculation, SubActivityAvg, Orders)"
					+ "values ("
					+ subactivity.getId() + "," 
					+ subactivity.getActivityId() + ",'"
					+ subactivity.getSubActivityName() + "','"
					+ subactivity.getType() + "',"
					+ subactivity.getMaximumMark() + ","
					+ subactivity.getWeightage() + ","
					+ subactivity.getCalculation() + ","
					+ subactivity.getSubActivityAvg() + ","
					+ subactivity.getOrders() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			subactivity.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subactivity;
	}
	
	public void update(SubActivity subactivity) {
		try {
			String query = "update subactivity set"
					+ " SubActivityName = '" + subactivity.getSubActivityName()
					+ "', Type = '" + subactivity.getType()
					+ "', MaximumMark = " + subactivity.getMaximumMark() 
					+ ", Weightage = " + subactivity.getWeightage() 
					+ ", Calculation = " + subactivity.getCalculation()
					+ ", Orders = " + subactivity.getOrders()
					+ " where Id = " + subactivity.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from subactivity where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
