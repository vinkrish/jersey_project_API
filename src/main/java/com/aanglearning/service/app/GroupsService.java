package com.aanglearning.service.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.Groups;
import com.aanglearning.model.app.UserGroup;
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
					+ group.isClas() + ","
					+ group.getCreatedBy() + ",'"
					+ group.getCreatedDate() + "',"
					+ group.isActive() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			group.setId(pk);
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			
			UserGroup userGroup = new UserGroup();
			userGroup.setActive(true);
			userGroup.setGroupId(pk);
			userGroup.setRole("admin");
			userGroup.setUserId(group.getCreatedBy());
			
			String query2 = "insert into user_group(Id, UserId, Role, GroupId, IsActive) values ("
					+ userGroup.getId() + "," 
					+ userGroup.getUserId() + ",'"
					+ userGroup.getRole() + "',"
					+ userGroup.getGroupId() + ","
					+ userGroup.isActive() + ")";
			stmt.executeUpdate(query2, Statement.RETURN_GENERATED_KEYS);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}
	
	public Groups getGroup(long groupId) {
		String query = "select * from groups where Id = " + groupId ;
		Groups groups = new Groups();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				groups.setId(rs.getLong("Id"));
				groups.setName(rs.getString("Name"));
				groups.setSectionId(rs.getLong("SectionId"));
				groups.setSection(rs.getBoolean("IsSection"));
				groups.setClassId(rs.getLong("ClassId"));
				groups.setClas(rs.getBoolean("IsClass"));
				groups.setCreatedBy(rs.getLong("CreatedBy"));
				groups.setCreatedDate(rs.getString("CreatedDate"));
				groups.setActive(rs.getBoolean("IsActive"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	public List<Groups> getStudentGroups(long userId) {
		List<Groups> groups = new ArrayList<>();
		String query = "select * from groups where "
				+ "Id in (select GroupId from user_group where UserId = " + userId + " and Role='student')";
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Groups group = new Groups();
				group.setId(rs.getLong("Id"));
				group.setName(rs.getString("Name"));
				group.setSectionId(rs.getLong("SectionId"));
				group.setSection(rs.getBoolean("IsSection"));
				group.setClassId(rs.getLong("ClassId"));
				group.setClas(rs.getBoolean("IsClass"));
				group.setCreatedBy(rs.getLong("CreatedBy"));
				group.setCreatedDate(rs.getString("CreatedDate"));
				group.setActive(rs.getBoolean("IsActive"));
				groups.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	public List<Groups> getTeacherGroups(long userId) {
		List<Groups> groups = new ArrayList<>();
		String query = "select * from groups where "
				+ "Id in (select GroupId from user_group where UserId = " + userId + " and (Role='admin' or Role='teacher'))";
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Groups group = new Groups();
				group.setId(rs.getLong("Id"));
				group.setName(rs.getString("Name"));
				group.setSectionId(rs.getLong("SectionId"));
				group.setSection(rs.getBoolean("IsSection"));
				group.setClassId(rs.getLong("ClassId"));
				group.setClas(rs.getBoolean("IsClass"));
				group.setCreatedBy(rs.getLong("CreatedBy"));
				group.setCreatedDate(rs.getString("CreatedDate"));
				group.setActive(rs.getBoolean("IsActive"));
				groups.add(group);
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
					+ ", IsClass = " + groups.isClas()
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
