package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.DeletedMessage;
import com.aanglearning.service.DatabaseUtil;

public class DeletedMessageService {
	
	Connection connection;

	public DeletedMessageService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DeletedMessage add(DeletedMessage message) {
		delete(message.getMessageId());
		
		String query = "insert into deleted_message(MessageId, SenderId, RecipientId, GroupId, DeletedAt) "
				+ "values (?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, message.getMessageId());
	    	preparedStatement.setLong(2, message.getSenderId());
	    	preparedStatement.setLong(3, message.getRecipientId());
	    	preparedStatement.setLong(4, message.getGroupId());
	    	preparedStatement.setLong(5, message.getDeletedAt());
	    	preparedStatement.executeUpdate();
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			message.setId(pk);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	public List<DeletedMessage> getGroupDeletedMessages(long groupId) {
		String query = "select * from deleted_message where GroupId=? order by A.Id desc";
		List<DeletedMessage> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, groupId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedMessage message = new DeletedMessage();
				message.setId(rs.getLong("Id"));
				message.setMessageId(rs.getLong("MessageId"));
				message.setSenderId(rs.getLong("SenderId"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setDeletedAt(rs.getLong("DeletedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<DeletedMessage> getGroupDeletedMessagesFromId(long groupId, long id) {
		String query = "select * from deleted_message where GroupId=? and Id<? order by A.Id desc";
		List<DeletedMessage> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				DeletedMessage message = new DeletedMessage();
				message.setId(rs.getLong("Id"));
				message.setMessageId(rs.getLong("MessageId"));
				message.setSenderId(rs.getLong("SenderId"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setDeletedAt(rs.getLong("DeletedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	private void delete(long id) {
		String query = "delete from message where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
