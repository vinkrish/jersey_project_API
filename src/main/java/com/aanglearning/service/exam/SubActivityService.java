package com.aanglearning.service.exam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.SubActivity;
import com.aanglearning.service.DatabaseUtil;

public class SubActivityService {
	Statement stmt;

	public SubActivityService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
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
			String query = "insert into subactivity(Id, ActivityId, SubActivityName, Type, MaximumMark, Weightage, SubActivityAvg, Orders)"
					+ "values ("
					+ subactivity.getId() + "," 
					+ subactivity.getActivityId() + ",'"
					+ subactivity.getSubActivityName() + "','"
					+ subactivity.getType() + "',"
					+ subactivity.getMaximumMark() + ","
					+ subactivity.getWeightage() + ","
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
