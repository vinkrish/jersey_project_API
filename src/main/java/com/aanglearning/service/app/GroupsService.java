package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.Groups;
import com.aanglearning.model.app.UserGroup;
import com.aanglearning.service.DatabaseUtil;

public class GroupsService {
	Statement stmt;
	Connection connection;

	public GroupsService() {
		try {
			connection = DatabaseUtil.getConnection();
			stmt = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Groups add(final Groups group) {
		try {
			String query = "insert into groups(Id, Name, IsSchool, SectionId, IsSection, ClassId, IsClass, CreatedBy, CreatorName, CreatorRole, CreatedDate, IsActive, SchoolId) "
					+ "values ("
					+ group.getId() + ",'" 
					+ group.getName() + "',"
					+ group.isSchool() + ","
					+ group.getSectionId() + ","
					+ group.isSection() + ","
					+ group.getClassId() + ","
					+ group.isClas() + ","
					+ group.getCreatedBy() + ",'"
					+ group.getCreatorName() + "','"
					+ group.getCreatorRole() + "','"
					+ group.getCreatedDate() + "',"
					+ group.isActive() + ","
					+ group.getSchoolId() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			group.setId(pk);
			final long groupId = pk;
			
			UserGroup userGroup = new UserGroup();
			userGroup.setActive(true);
			userGroup.setGroupId(pk);
			userGroup.setRole(group.getCreatorRole());
			userGroup.setUserId(group.getCreatedBy());
			
			String query2 = "insert into user_group(Id, UserId, Role, GroupId, IsActive) values ("
					+ userGroup.getId() + "," 
					+ userGroup.getUserId() + ",'"
					+ userGroup.getRole() + "',"
					+ userGroup.getGroupId() + ","
					+ userGroup.isActive() + ")";
			stmt.executeUpdate(query2);
			
			new Thread(new Runnable() {
				  @Override
				  public void run() {
					  if(group.isClas()) {
						  insertUserGroup(groupId, "select Id from student where ClassId = " + group.getClassId());
					  } else if(group.isSection()) {
						  insertUserGroup(groupId, "select Id from student where SectionId = " + group.getSectionId());
					  } else if(group.isSchool()) {
						  insertTeacherUserGroup(groupId, "select Id from teacher where SchoolId = " + group.getSchoolId() + " and Id != " + group.getCreatedBy());
					  }
				  }
				}).start();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}
	
	private void insertUserGroup(long groupId, String query) {
		List<Long> studentList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				studentList.add(rs.getLong("Id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "insert into user_group(UserId, Role, GroupId, IsActive) values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(sql);
		    for(Long student: studentList) {
		    	preparedStatement.setLong(1, student);
		    	preparedStatement.setString(2, "student");
		    	preparedStatement.setLong(3, groupId);
		    	preparedStatement.setBoolean(4, true);
		    	preparedStatement.executeUpdate();
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertTeacherUserGroup(long groupId, String query) {
		List<Long> teacherList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				teacherList.add(rs.getLong("Id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "insert into user_group(UserId, Role, GroupId, IsActive) values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(sql);
		    for(Long teacher: teacherList) {
		    	preparedStatement.setLong(1, teacher);
		    	preparedStatement.setString(2, "teacher");
		    	preparedStatement.setLong(3, groupId);
		    	preparedStatement.setBoolean(4, true);
		    	preparedStatement.executeUpdate();
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Groups getGroup(long groupId) {
		String query = "select * from groups where Id = " + groupId ;
		Groups groups = new Groups();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				groups.setId(rs.getLong("Id"));
				groups.setName(rs.getString("Name"));
				groups.setSchool(rs.getBoolean("IsSchool"));
				groups.setSectionId(rs.getLong("SectionId"));
				groups.setSection(rs.getBoolean("IsSection"));
				groups.setClassId(rs.getLong("ClassId"));
				groups.setClas(rs.getBoolean("IsClass"));
				groups.setCreatedBy(rs.getLong("CreatedBy"));
				groups.setCreatorName(rs.getString("CreatorName"));
				groups.setCreatorRole(rs.getString("CreatorRole"));
				groups.setCreatedDate(rs.getString("CreatedDate"));
				groups.setActive(rs.getBoolean("IsActive"));
				groups.setSchoolId(rs.getLong("SchoolId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	public List<Groups> getStudentGroups(long userId) {
		String query1 = "select * from groups where "
				+ "Id in (select GroupId from user_group where UserId = " + userId + " and Role='student')";
		
		String query2 = "select * from groups where IsSchool = 1 and SchoolId = (select SchoolId from student where Id = " + userId + ")";
		
		List<Groups> groups = new ArrayList<>();
		groups.addAll(getGroups(query1));
		groups.addAll(getGroups(query2));
		return groups;
	}
	
	public List<Groups> getStudGroups(long userId) {
		String query = "select * from groups where "
				+ "Id in (select GroupId from user_group where UserId = " + userId + " and Role='student')";
		return getGroups(query);
	}
	
	public List<Groups> getStudGroupsAboveId(long userId, long id) {
		String query = "select * from groups where "
				+ "Id in (select GroupId from user_group where UserId = " + userId + " and Role='student') and Id > " + id;
		return getGroups(query);
	}
	
	public List<Groups> getSchoolGroups(long schoolId) {
		String query = "select * from groups where IsSchool = 1 and SchoolId = " + schoolId;
		return getGroups(query);
	}
	
	public List<Groups> getSchoolGroupsAboveId(long schoolId, long id) {
		String query = "select * from groups where IsSchool = 1 and SchoolId = " + schoolId + " and Id > " + id;
		return getGroups(query);
	}
	
	public List<Groups> getTeacherGroups(long userId) {
		String query = "select * from groups where "
				+ "Id in (select GroupId from user_group where UserId = " + userId + " and (Role='admin' or Role='teacher'))";
		return getGroups(query);
	}
	
	public List<Groups> getTeacherGroupsAboveId(long userId, long id) {
		String query = "select * from groups where "
				+ "Id in (select GroupId from user_group where UserId = " + userId + " and (Role='admin' or Role='teacher')) and Id > " + id;
		return getGroups(query);
	}
	
	public List<Groups> getPrincipalGroups(long userId) {
		String sql = "select * from groups where CreatorRole = 'principal' and CreatedBy = " + userId;
		return getGroups(sql);
	}
	
	public List<Groups> getPrincipalGroupsAboveId(long userId, long id) {
		String sql = "select * from groups where CreatorRole = 'principal' and CreatedBy = " + userId + " and Id > " + id;
		return getGroups(sql);
	}
	
	public List<Groups> getAllGroups(long schoolId) {
		String query = "select * from groups where "
				+ "ClassId in (select Id from class where SchoolId = " + schoolId + ") and CreatorRole != 'principal'";
		return getGroups(query);
	}
	
	public List<Groups> getAllGroupsAboveId(long schoolId, long id) {
		String query = "select * from groups where "
				+ "ClassId in (select Id from class where SchoolId = " + schoolId + ") and CreatorRole != 'principal' and Id > " + id;
		return getGroups(query);
	}
	
	private List<Groups> getGroups(String query) {
		List<Groups> groups = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Groups group = new Groups();
				group.setId(rs.getLong("Id"));
				group.setName(rs.getString("Name"));
				group.setSchool(rs.getBoolean("IsSchool"));
				group.setSectionId(rs.getLong("SectionId"));
				group.setSection(rs.getBoolean("IsSection"));
				group.setClassId(rs.getLong("ClassId"));
				group.setClas(rs.getBoolean("IsClass"));
				group.setCreatedBy(rs.getLong("CreatedBy"));
				group.setCreatorName(rs.getString("CreatorName"));
				group.setCreatorRole(rs.getString("CreatorRole"));
				group.setCreatedDate(rs.getString("CreatedDate"));
				group.setActive(rs.getBoolean("IsActive"));
				group.setSchoolId(rs.getLong("SchoolId"));
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
			String query = "delete from groups where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
