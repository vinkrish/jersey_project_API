package com.aanglearning.service.cce;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.cce.CceAspectPrimary;
import com.aanglearning.service.JDBC;

public class CceAspectPrimaryService {
	Statement stmt;

	public CceAspectPrimaryService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CceAspectPrimary> getCceAspectPrimarys(long topicId) {
		String query = "select * from cce_aspect_primary where TopicId = " + topicId;
		List<CceAspectPrimary> aspectPrimarys = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				CceAspectPrimary aspectPrimary = new CceAspectPrimary();
				aspectPrimary.setId(rs.getLong("Id"));
				aspectPrimary.setName(rs.getString("Name"));
				aspectPrimary.setTopicId(rs.getLong("TopicId"));
				aspectPrimarys.add(aspectPrimary);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aspectPrimarys;
	}
	
	public CceAspectPrimary add(CceAspectPrimary aspectPrimary) {
		try {
			String query = "insert into cce_aspect_primary(Id, Name, TopicId) "
					+ "values (" 
					+ aspectPrimary.getId() + ",'" 
					+ aspectPrimary.getName() + "',"
					+ aspectPrimary.getTopicId() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			aspectPrimary.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aspectPrimary;
	}
	
	public void update(CceAspectPrimary aspectPrimary) {
		try {
			String query = "update cce_aspect_primary set Name = '" + aspectPrimary.getName()
			+ "' where Id=" + aspectPrimary.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from cce_aspect_primary where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
