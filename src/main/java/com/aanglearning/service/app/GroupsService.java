package com.aanglearning.service.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.aanglearning.model.app.Groups;
import com.aanglearning.service.JDBC;

public class GroupsService {
	
	Statement stmt = null;

	public GroupsService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Groups add(Groups group) {
		try {
			String query = "insert into groups(Id, Name, SectionId, IsSection, ClassId, IsClass, CreatedBy, CreatedDate, IsActive) "
					+ "values ("
					+ group.getId() + ",'" 
					+ group.getName() + "',"
					+ group.getSectionId() + ","
					+ group.isSection() + ","
					+ group.getClassId() + ","
					+ group.isClass() + ","
					+ group.getCreatedBy() + ",'"
					+ group.getCreatedDate() + "',"
					+ group.isActive() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			group.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}
	
	public Groups getGroup(long groupId) {
		String query = "select * from groups "
				+ "where "
				+ "Id = " + groupId ;
		Groups groups = new Groups();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				groups.setId(rs.getLong("Id"));
				groups.setName(rs.getString("Name"));
				groups.setSectionId(rs.getLong("SectionId"));
				groups.setSection(rs.getBoolean("IsSection"));
				groups.setClassId(rs.getLong("ClassId"));
				groups.setClass(rs.getBoolean("IsClass"));
				groups.setCreatedBy(rs.getLong("CreatedBy"));
				groups.setCreatedDate(rs.getString("CreatedDate"));
				groups.setActive(rs.getBoolean("IsActive"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	public void update(Groups groups) {
		try {
			String query = "update groups set"
					+ " Name = '" + groups.getName()
					+ "', SectionId = " + groups.getSectionId()
					+ ", IsSection = " + groups.isSection()
					+ ", ClassId = " + groups.getClassId()
					+ ", IsClass = " + groups.isClass()
					+ ", IsActive = " + groups.isActive()
					+ " where Id = " + groups.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from group where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
