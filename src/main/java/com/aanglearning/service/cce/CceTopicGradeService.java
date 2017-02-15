package com.aanglearning.service.cce;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.cce.CceTopicGrade;
import com.aanglearning.service.JDBC;

public class CceTopicGradeService {
	Statement stmt = null;

	public CceTopicGradeService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CceTopicGrade> getCceTopicGrades(long topicId) {
		String query = "select * from cce_topic_grade where TopicId = " + topicId;
		List<CceTopicGrade> topicGrades = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				CceTopicGrade topicGrade = new CceTopicGrade();
				topicGrade.setId(rs.getLong("Id"));
				topicGrade.setTopicId(rs.getLong("TopicId"));
				topicGrade.setGrade(rs.getString("Grade"));
				topicGrade.setValue(rs.getInt("Value"));
				topicGrades.add(topicGrade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topicGrades;
	}
	
	public CceTopicGrade add(CceTopicGrade topicGrade) {
		try {
			String query = "insert into cce_topic_grade(Id, TopicId, Grade, Value) "
					+ "values (" 
					+ topicGrade.getId() + "," 
					+ topicGrade.getTopicId() + ",'"
					+ topicGrade.getGrade() + "',"
					+ topicGrade.getValue() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			topicGrade.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topicGrade;
	}
	
	public void update(CceTopicGrade topicGrade) {
		try {
			String query = "update cce_topic_grade set Grade = '" + topicGrade.getGrade()
			+ "', Value = '" + topicGrade.getValue() + "' where Id=" + topicGrade.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from cce_topic_grade where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
