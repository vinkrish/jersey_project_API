package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.GroupUsers;
import com.aanglearning.model.app.Groups;
import com.aanglearning.model.app.UserGroup;
import com.aanglearning.resource.app.GroupsResource;
import com.aanglearning.resource.entity.StudentResource;
import com.aanglearning.resource.entity.TeacherResource;
import com.aanglearning.service.JDBC;

public class UserGroupService {
	Connection connection = null;
	StudentResource studentResource;
	TeacherResource teacherResource;
	GroupsResource groupsResource;

	public UserGroupService() {
		connection = JDBC.getConnection();
		studentResource = new StudentResource();
		teacherResource = new TeacherResource();
		groupsResource = new GroupsResource();
	}
	
	public void add(List<UserGroup> userGroupList) {
		String query = "insert into user_group(Id, UserId, Role, GroupId, IsActive) values (?,?,?,?,?)";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(UserGroup userGroup: userGroupList) {
		    	preparedStatement.setLong(1, userGroup.getId());
		    	preparedStatement.setLong(2, userGroup.getUserId());
		    	preparedStatement.setString(3, userGroup.getRole());
		    	preparedStatement.setLong(4, userGroup.getGroupId());
		    	preparedStatement.setBoolean(5, true);
		    	preparedStatement.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<UserGroup> getUserGroups(long groupId) {
		String query1 = "select A.*, B.StudentName as Name from user_group A, student B "
				+ "where A.GroupId=? and A.Role='student' and A.UserId=B.Id";
		String query2 = "select A.*, B.TeacherName as Name from user_group A, teacher B "
				+ "where A.GroupId=? and (A.Role='admin' or A.Role='teacher') and  A.UserId=B.Id";
		
		List<UserGroup> userGroups = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query1);
			preparedStatement.setLong(1, groupId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				UserGroup userGroup = new UserGroup();
				userGroup.setId(rs.getLong("Id"));
				userGroup.setUserId(rs.getLong("UserId"));
				userGroup.setName(rs.getString("Name"));
				userGroup.setRole(rs.getString("Role"));
				userGroup.setGroupId(rs.getLong("GroupId"));
				userGroup.setActive(rs.getBoolean("IsActive"));
				userGroups.add(userGroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query2);
			preparedStatement.setLong(1, groupId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				UserGroup userGroup = new UserGroup();
				userGroup.setId(rs.getLong("Id"));
				userGroup.setUserId(rs.getLong("UserId"));
				userGroup.setName(rs.getString("Name"));
				userGroup.setRole(rs.getString("Role"));
				userGroup.setGroupId(rs.getLong("GroupId"));
				userGroup.setActive(rs.getBoolean("IsActive"));
				userGroups.add(userGroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userGroups;
	}
	
	public GroupUsers getGroupUsers(long groupId) {
		GroupUsers groupUsers = new GroupUsers();
		groupUsers.setUserGroupList(getUserGroups(groupId));
		Groups groups = groupsResource.getGroupById(groupId);
		if(groups.isClas()) {
			groupUsers.setStudents(studentResource.getClassGroupUsers(groupId, groups.getClassId()));
			groupUsers.setTeachers(teacherResource.getClassGroupUsers(groupId, groups.getClassId()));
		} else if(groups.isSection()) {
			groupUsers.setStudents(studentResource.getSectionGroupUsers(groupId, groups.getSectionId()));
			groupUsers.setTeachers(teacherResource.getSectionGroupUsers(groupId, groups.getSectionId()));
		}
		return groupUsers;
	}
	
	public void delete(long groupId) {
		String query = "delete from user_group where GroupId=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, groupId);
		    	preparedStatement.executeUpdate();
		    connection.commit();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void deleteUsers(List<UserGroup> userGroups) {
		String query = "delete from user_group where Id=? and GroupId=?";
		try{
		    connection.setAutoCommit(false);
		    for(UserGroup userGroup: userGroups) {
		    	PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, userGroup.getId());
		    	preparedStatement.setLong(2, userGroup.getGroupId());
		    	preparedStatement.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void add(UserGroup userGroup) {
		String query = "insert into user_group(Id, UserId, Role, GroupId, IsActive) values (?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, userGroup.getId());
		    	preparedStatement.setLong(2, userGroup.getUserId());
		    	preparedStatement.setString(3, userGroup.getRole());
		    	preparedStatement.setLong(4, userGroup.getGroupId());
		    	preparedStatement.setBoolean(5,  userGroup.isActive());
		    	preparedStatement.executeUpdate();
		    connection.commit();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
