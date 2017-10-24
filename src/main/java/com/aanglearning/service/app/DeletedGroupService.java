package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.DeletedGroup;
import com.aanglearning.service.DatabaseUtil;

public class DeletedGroupService {
	Connection connection;

	public DeletedGroupService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DeletedGroup add(DeletedGroup group) {
		delete(group.getGroupId());
		
		String query = "insert into deleted_group(SenderId, GroupId, SchoolId, DeletedAt) "
				+ "values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, group.getSenderId());
	    	preparedStatement.setLong(2, group.getGroupId());
	    	preparedStatement.setLong(3, group.getSchoolId());
	    	preparedStatement.setLong(4, group.getDeletedAt());
	    	preparedStatement.executeUpdate();
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			group.setId(pk);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return group;
	}
	
	public List<DeletedGroup> getDeletedGroups(long schoolId) {
		String query = "select * from deleted_group where SchoolId=? order by Id desc";
		List<DeletedGroup> groups = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, schoolId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedGroup group = new DeletedGroup();
				group.setId(rs.getLong("Id"));
				group.setSenderId(rs.getLong("SenderId"));
				group.setGroupId(rs.getLong("GroupId"));
				group.setSchoolId(rs.getLong("SchoolId"));
				group.setDeletedAt(rs.getLong("DeletedAt"));
				groups.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	public List<DeletedGroup> getDeletedGroupsAboveId(long schoolId, long id) {
		String query = "select * from deleted_group where SchoolId=? and Id>?";
		List<DeletedGroup> groups = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, schoolId);
			preparedStatement.setLong(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedGroup group = new DeletedGroup();
				group.setId(rs.getLong("Id"));
				group.setSenderId(rs.getLong("SenderId"));
				group.setGroupId(rs.getLong("GroupId"));
				group.setSchoolId(rs.getLong("SchoolId"));
				group.setDeletedAt(rs.getLong("DeletedAt"));
				groups.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	private void delete(long id) {
		String query1 = "delete from groups where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query1);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
		String query2 = "delete from user_group where GroupId=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query2);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
