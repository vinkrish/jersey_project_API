package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.UserGroup;
import com.aanglearning.service.JDBC;

public class UserGroupService {
	Connection connection = null;

	public UserGroupService() {
		connection = JDBC.getConnection();
	}
	
	public void add(List<UserGroup> userGroupList) {
		String query = "insert into user_group(Id, UserId, Role, GroupId, CreatedDate, IsActive) values (?,?,?,?,?,?)";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(UserGroup userGroup: userGroupList) {
		    	preparedStatement.setLong(1, userGroup.getId());
		    	preparedStatement.setLong(2, userGroup.getUserId());
		    	preparedStatement.setString(3, userGroup.getRole());
		    	preparedStatement.setLong(4, userGroup.getGroupId());
		    	preparedStatement.setString(5, userGroup.getCreatedDate());
		    	preparedStatement.setBoolean(6,  userGroup.isActive());
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
		String query = "select * from user_group where GroupId=?";
		List<UserGroup> userGroups = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, groupId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				UserGroup userGroup = new UserGroup();
				userGroup.setId(rs.getLong("Id"));
				userGroup.setUserId(rs.getLong("UserId"));
				userGroup.setRole(rs.getString("Role"));
				userGroup.setGroupId(rs.getLong("GroupId"));
				userGroup.setCreatedDate(rs.getString("CreatedDate"));
				userGroup.setActive(rs.getBoolean("IsActive"));
				userGroups.add(userGroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userGroups;
	}
	
	public void delete(long groupId) {
		String query = "delete from user_group where GroupId=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, groupId);
		    	preparedStatement.executeUpdate();
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
