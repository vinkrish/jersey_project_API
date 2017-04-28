package com.aanglearning.service.cce;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.cce.CceTopicPrimary;
import com.aanglearning.service.JDBC;

public class CceTopicPrimaryService {
	Statement stmt;

	public CceTopicPrimaryService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CceTopicPrimary> getCceTopicPrimarys(long sectionHeadingId) {
		String query = "select * from cce_topic_primary where SectionHeadingId = " + sectionHeadingId;
		List<CceTopicPrimary> topicPrimarys = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				CceTopicPrimary topicPrimary = new CceTopicPrimary();
				topicPrimary.setId(rs.getLong("Id"));
				topicPrimary.setName(rs.getString("Name"));
				topicPrimary.setSectionHeadingId(rs.getLong("SectionHeadingId"));
				topicPrimary.setEvaluation(rs.getInt("Evaluation"));
				topicPrimarys.add(topicPrimary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topicPrimarys;
	}
	
	public CceTopicPrimary add(CceTopicPrimary topicPrimary) {
		try {
			String query = "insert into cce_topic_primary(Id, Name, SectionHeadingId, Evaluation) "
					+ "values (" 
					+ topicPrimary.getId() + ",'" 
					+ topicPrimary.getName() + "',"
					+ topicPrimary.getSectionHeadingId() + ","
					+ topicPrimary.getEvaluation() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			topicPrimary.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topicPrimary;
	}
	
	public void update(CceTopicPrimary topicPrimary) {
		try {
			String query = "update cce_topic_primary set Name = '" + topicPrimary.getName()
			+ "', Evaluation = " + topicPrimary.getEvaluation() + " where Id=" + topicPrimary.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from cce_topic_primary where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
