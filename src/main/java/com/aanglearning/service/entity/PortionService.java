package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Portion;
import com.aanglearning.service.JDBC;

public class PortionService {
	Statement stmt = null;

	public PortionService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Portion> getPortions(long classId, long subjectId) {
		String query = "select * from portion where ClassId = " + classId + " and SubjectId = " + subjectId;
		List<Portion> portions = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Portion portion = new Portion();
				portion.setId(rs.getLong("Id"));
				portion.setClassId(rs.getLong("ClassId"));
				portion.setSubjectId(rs.getLong("SubjectId"));
				portion.setPortionName(rs.getString("PortionName"));
				portions.add(portion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return portions;
	}
	
	public Portion add(Portion portion) {
		try {
			String query = "insert into portion(Id, ClassId, SubjectId, PortionName) "
					+ "values (" 
					+ portion.getId() + ","
					+ portion.getClassId() + ","
					+ portion.getSubjectId() + ",'" 
					+ portion.getPortionName() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			portion.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return portion;
	}
	
	public void update(Portion portion) {
		try {
			String query = "update portion set PortionName = '" + portion.getPortionName() 
			+ "' where Id=" + portion.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from portion where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
