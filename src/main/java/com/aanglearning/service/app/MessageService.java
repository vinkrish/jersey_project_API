package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.Message;
import com.aanglearning.service.JDBC;

public class MessageService {
	Connection connection = null;

	public MessageService() {
		connection = JDBC.getConnection();
	}
	
	public Message add(Message message) {
		String query = "insert into message(Id, SenderId, SenderRole, RecipientId, RecipientRole, GroupId, MessageType, MessageBody, ImageUrl, CreatedAt) "
				+ "values (?,?,?,?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, message.getId());
	    	preparedStatement.setLong(2, message.getSenderId());
	    	preparedStatement.setString(3, message.getSenderRole());
	    	preparedStatement.setLong(4, message.getRecipientId());
	    	preparedStatement.setString(5, message.getRecipientRole());
	    	preparedStatement.setLong(6, message.getGroupId());
	    	preparedStatement.setString(7, message.getMessageType());
	    	preparedStatement.setString(8, message.getMessageBody());
	    	preparedStatement.setString(9, message.getImageUrl());
	    	preparedStatement.setString(10, message.getCreatedAt());
	    	preparedStatement.executeUpdate();
		    
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			message.setId(pk);
			
			/*if(message.getGroupId() == 0) {
				String sql = "insert into message_recipient(RecipientId, Role, MessageId) values(?,?,?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setLong(1, message.getRecipientId());
				statement.setString(2, message.getRecipientRole());
		    	statement.setLong(3, message.getId());
		    	preparedStatement.executeUpdate();
		    	connection.commit();
			}*/
			
		} catch(Exception e) {
		    e.printStackTrace();
		}
		
		return message;
	}
	
	public List<Message> getMessages(long senderId, String senderRole, long recipientId, String recipientRole) {
		String query = "select * from message where SenderId=? and SenderRole=? and RecipientId=? and RecipeintRole=? order by Id desc limit 100";
		List<Message> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, senderId);
			preparedStatement.setString(2, senderRole);
			preparedStatement.setLong(3, recipientId);
			preparedStatement.setString(4, recipientRole);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Message message = new Message();
				message.setId(rs.getLong("Id"));
				message.setSenderId(rs.getLong("SenderId"));
				message.setSenderRole(rs.getString("SenderRole"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setMessageType(rs.getString("MessageType"));
				message.setMessageBody(rs.getString("MessageBody"));
				message.setImageUrl(rs.getString("ImageUrl"));
				message.setCreatedAt(rs.getString("CreatedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<Message> getMessagesFromId(long senderId, String senderRole, long recipientId, String recipientRole, long messageId) {
		String query = "select * from message where SenderId=? and SenderRole=? and RecipientId=? and RecipeintRole=? and Id<? order by Id desc limit 100";
		List<Message> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, senderId);
			preparedStatement.setLong(2, recipientId);
			preparedStatement.setLong(3, messageId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Message message = new Message();
				message.setId(rs.getLong("Id"));
				message.setSenderId(rs.getLong("SenderId"));
				message.setSenderRole(rs.getString("SenderRole"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setMessageType(rs.getString("MessageType"));
				message.setMessageBody(rs.getString("MessageBody"));
				message.setImageUrl(rs.getString("ImageUrl"));
				message.setCreatedAt(rs.getString("CreatedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<Message> getGroupMessages(long groupId) {
		String query = "select A.*, B.TeacherName from message A, teacher B where GroupId=? and A.SenderId=B.Id order by A.Id desc limit 20";
		List<Message> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, groupId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Message message = new Message();
				message.setId(rs.getLong("Id"));
				message.setSenderId(rs.getLong("SenderId"));
				message.setSenderRole(rs.getString("SenderRole"));
				message.setSenderName(rs.getString("TeacherName"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setMessageType(rs.getString("MessageType"));
				message.setMessageBody(rs.getString("MessageBody"));
				message.setImageUrl(rs.getString("ImageUrl"));
				message.setCreatedAt(rs.getString("CreatedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<Message> getGroupMessagesFromId(long groupId, long messageId) {
		String query = "select A.*, B.TeacherName from message A, teacher B where GroupId=? and A.SenderId=B.Id and A.Id<? order by A.Id desc limit 20";
		List<Message> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, messageId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Message message = new Message();
				message.setId(rs.getLong("Id"));
				message.setSenderId(rs.getLong("SenderId"));
				message.setSenderRole(rs.getString("SenderRole"));
				message.setSenderName(rs.getString("TeacherName"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setMessageType(rs.getString("MessageType"));
				message.setMessageBody(rs.getString("MessageBody"));
				message.setImageUrl(rs.getString("ImageUrl"));
				message.setCreatedAt(rs.getString("CreatedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public void delete(long id) {
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
