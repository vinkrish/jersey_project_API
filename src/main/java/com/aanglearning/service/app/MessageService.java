package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.aanglearning.model.app.Message;
import com.aanglearning.service.DatabaseUtil;
import com.aanglearning.service.FCMPost;

public class MessageService {
	Connection connection;

	public MessageService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			
			if(message.getGroupId() == 0) {
				String name = "";
				String username = "";
				String table = "";
				if(message.getRecipientRole().equals("teacher") 
						|| message.getRecipientRole().equals("principal") 
						|| message.getRecipientRole().equals("admin")) {
					table = "teacher";
				} else if(message.getRecipientRole().equals("student")) {
					table = "student";
				}
				
				String query_search = "select Name, Username from " + table + " where Id = " + message.getRecipientId();
				try {
					ResultSet resultSet = connection.createStatement().executeQuery(query_search);
					if (resultSet.next()) {
						name = resultSet.getString("Name");
						username = resultSet.getString("Username");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(!username.equals("")) {
					JSONObject msg = new JSONObject();
					msg.put("sender_id", message.getSenderId());
					msg.put("sender_name", name);
					msg.put("message", message.getMessageBody());
					
					JSONObject notification = new JSONObject();
					notification.put("title", name);
					notification.put("body", message.getMessageBody());
					
				    JSONObject fcm = new JSONObject();
				    fcm.put("to", getFCMToken(username));
				    fcm.put("notification", notification);
				    fcm.put("data", msg);
				    fcm.put("time_to_live", 43200);
				    
				    FCMPost fcmPost = new FCMPost();
				    switch(message.getRecipientRole()){
				    case "teacher":
				    	fcmPost.post(fcm.toString(), "AAAA_yeO7iY:APA91bFLcSMNj8TbaHs6wJcKViRnRSGiCBhkai3zxvsekMQ5taBCUPgXvGsq-7PsbNuqGZ7Bh0iKre-NWhcmM2rwFd3-kaFTsPCgHvGtfrPl5ZiQ6CWNuikRRKEkpZXLFtDNb3Hy7R9J");
				    	break;
				    case "student":
				    	fcmPost.post(fcm.toString(), "AAAAN54dwVg:APA91bG8KE77LmQ3Fe-LJrkDVST6HDe-keM32Qrx6bhKFAgarh5Fl0SktXQKF2ytsqXVIicZ5-eef07hApRzYew9ZU0rQCJjGaPRnh8novM0tB3AeuutO2GVPJ83GD-sFTCFow1x_pDe");
				    	break;
				    case "principal":
				    	fcmPost.post(fcm.toString(), "AAAAdr2uwq8:APA91bEvgBP86EcOXDY4kBEeKxeS7YQTftS6UkrC973pDohHR9UyEKIpkHsYno94KVPqZj1YyvdoTwZF3uwL1pEI3GJUPV2TKJywP9SzDp5_H8Evo5nrsPJ-b7jOLOr8GMAKHMKbGX8p");
				    	break;
				    default:
				    	break;
				    }
				}
				
			}
			
		} catch(Exception e) {
		    e.printStackTrace();
		}
		
		return message;
	}
	
	private String getFCMToken(String user) {
		String fcmToken = "";
		String query = "select FcmToken from authorization where User = '" + user + "'";
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			if (rs.next()) {
				fcmToken = rs.getString("FcmToken");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fcmToken;
	}
	
	public List<Message> getMessagesAboveId(long senderId, String senderRole, long recipientId, String recipientRole, long messageId) {
		String query = "select * from message where "
				+ "((SenderId=? and SenderRole=? and RecipientId=? and RecipientRole=?) or (SenderId=? and SenderRole=? and RecipientId=? and RecipientRole=?)) "
				+ "and Id>? order by Id desc";
		List<Message> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, senderId);
			preparedStatement.setString(2, senderRole);
			preparedStatement.setLong(3, recipientId);
			preparedStatement.setString(4, recipientRole);
			preparedStatement.setLong(5, recipientId);
			preparedStatement.setString(6, recipientRole);
			preparedStatement.setLong(7, senderId);
			preparedStatement.setString(8, senderRole);
			preparedStatement.setLong(9, messageId);
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
	
	public List<Message> getMessages(long senderId, String senderRole, long recipientId, String recipientRole) {
		String query = "select * from message where "
				+ "((SenderId=? and SenderRole=? and RecipientId=? and RecipientRole=?) or (SenderId=? and SenderRole=? and RecipientId=? and RecipientRole=?)) "
				+ "order by Id desc limit 100";
		List<Message> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, senderId);
			preparedStatement.setString(2, senderRole);
			preparedStatement.setLong(3, recipientId);
			preparedStatement.setString(4, recipientRole);
			preparedStatement.setLong(5, recipientId);
			preparedStatement.setString(6, recipientRole);
			preparedStatement.setLong(7, senderId);
			preparedStatement.setString(8, senderRole);
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
		String query = "select * from message where "
				+ "((SenderId=? and SenderRole=? and RecipientId=? and RecipientRole=?) or (SenderId=? and SenderRole=? and RecipientId=? and RecipientRole=?)) "
				+ "and Id<? order by Id desc limit 100";
		List<Message> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, senderId);
			preparedStatement.setString(2, senderRole);
			preparedStatement.setLong(3, recipientId);
			preparedStatement.setString(4, recipientRole);
			preparedStatement.setLong(5, recipientId);
			preparedStatement.setString(6, recipientRole);
			preparedStatement.setLong(7, senderId);
			preparedStatement.setString(8, senderRole);
			preparedStatement.setLong(9, messageId);
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
		String query = "select A.*, B.TeacherName from message A, teacher B where GroupId=? and A.SenderId=B.Id order by A.Id desc limit 100";
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
		String query = "select A.*, B.TeacherName from message A, teacher B where GroupId=? and A.SenderId=B.Id and A.Id<? order by A.Id desc limit 100";
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
				//message.setCreatedAt(new DateTime(rs.getTimestamp("CreatedAt")));
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
