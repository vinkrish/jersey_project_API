package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
		String query = "insert into message(Id, SenderId, SenderRole, RecipientId, RecipientRole, GroupId, MessageType, MessageBody, ImageUrl, VideoUrl, CreatedAt) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?)";
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
	    	preparedStatement.setString(10, message.getVideoUrl());
	    	preparedStatement.setString(11, message.getCreatedAt());
	    	preparedStatement.executeUpdate();
		    
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			message.setId(pk);
			
			if(message.getGroupId() == 0) {
				String username = "";
				String table = "";
				String recipientName = "";
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
						recipientName = resultSet.getString("Name");
						username = resultSet.getString("Username");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(!username.equals("")) {
					JSONObject msg = new JSONObject();
					msg.put("is_group", "false");
					msg.put("sender_id", message.getSenderId());
					msg.put("sender_name", message.getSenderName());
					msg.put("sender_role", message.getSenderRole());
					msg.put("message", message.getMessageBody());
					msg.put("recipient_name", recipientName);
					
					JSONObject notification = new JSONObject();
					notification.put("title", message.getSenderName());
					notification.put("body", message.getMessageBody());
					
				    JSONObject fcm = new JSONObject();
				    fcm.put("to", getFCMToken(username));
				    //fcm.put("notification", notification);
				    fcm.put("data", msg);
				    fcm.put("time_to_live", 43200);
				    
				    FCMPost fcmPost = new FCMPost();
				    switch(message.getRecipientRole()){
				    case "teacher":
				    	fcmPost.post(fcm.toString(), "AAAAsv0q3Fk:APA91bEhQTIbIB8fmY4YG2P5zq5KxsE3c_oCsuYnYaM6C_vGkan5lTmOM9-S2lavGy8K10p9SLJ2V3j4WMDXD7FQX9TVT1fRSmY8f0IV01TKYWVHONJk2tnd1TBLqiZ2fgnv7HGuwRvR");
				    	break;
				    case "student":
				    	fcmPost.post(fcm.toString(), "AAAANtOFq98:APA91bGLAt-wCJDBhzomz_GmlVW8TXyshKdR6NOzuKTOk0NgM29Ww7-tZzjxCjT0siEua6AQY7stUxRTnkf_8cD5QgypjWfOTn1UYnzOQOP6uAB7bR_SA0SkSlOmPi9gPp6iHJL4xAzw");
				    	break;
				    case "principal":
				    	fcmPost.post(fcm.toString(), "AAAApEO4pXs:APA91bFmJ__dGwm6LW1k2CJyn7vBkbclBMuMWDhPhH_WP-1RXKMcS66deLGOiYcY1dsCw-hfg3tBs5xDwYcrijcOMEkAmrvyKGbMhY4tFgIIev6SMvLi5TLnGw6zvvitezwl1LirdKqY");
				    	break;
				    case "admin":
				    	fcmPost.post(fcm.toString(), "AAAAaAXsuro:APA91bEk9zwtEyDPrQNuoHi_XyTrZo_T-kwXgwA9mzxT4SOscjqUzfEosIAytF-OlCnmo9R2HbJ7tu0KTS-UxFnXJVr01ma8nqnPtapn_WnCdxPudiFu4ByIg3GsrDTnGgogq_enR2BQ");
				    	break;
				    default:
				    	break;
				    }
				}
			} else {
				String groupName = "";
				String q = "select Name from groups where Id = " + message.getGroupId();
				try {
					ResultSet r = connection.createStatement().executeQuery(q);
					while (r.next()){
						groupName = r.getString("Name");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				JSONObject msg = new JSONObject();
				msg.put("group_name", groupName);
				msg.put("is_group", "true");
				msg.put("group_id", message.getGroupId());
				
				FCMPost fcmPost = new FCMPost();
				switch(message.getSenderRole()){
			    case "teacher":
			    	JSONObject fcm = new JSONObject();
				    fcm.put("registration_ids", getParentFCMToken(message.getGroupId()));
				    fcm.put("data", msg);
				    fcm.put("time_to_live", 43200);
				    fcmPost.post(fcm.toString(), "AAAANtOFq98:APA91bGLAt-wCJDBhzomz_GmlVW8TXyshKdR6NOzuKTOk0NgM29Ww7-tZzjxCjT0siEua6AQY7stUxRTnkf_8cD5QgypjWfOTn1UYnzOQOP6uAB7bR_SA0SkSlOmPi9gPp6iHJL4xAzw");
			    	break;
			    case "admin":
			    	JSONObject fcm2 = new JSONObject();
				    fcm2.put("registration_ids", getParentFCMToken(message.getGroupId()));
				    fcm2.put("data", msg);
				    fcm2.put("time_to_live", 43200);
				    fcmPost.post(fcm2.toString(), "AAAANtOFq98:APA91bGLAt-wCJDBhzomz_GmlVW8TXyshKdR6NOzuKTOk0NgM29Ww7-tZzjxCjT0siEua6AQY7stUxRTnkf_8cD5QgypjWfOTn1UYnzOQOP6uAB7bR_SA0SkSlOmPi9gPp6iHJL4xAzw");
				    
				    JSONObject fcm3 = new JSONObject();
				    fcm3.put("registration_ids", getTeacherFCMToken(message.getGroupId()));
				    fcm3.put("data", msg);
				    fcm3.put("time_to_live", 43200);
				    fcmPost.post(fcm3.toString(), "AAAAsv0q3Fk:APA91bEhQTIbIB8fmY4YG2P5zq5KxsE3c_oCsuYnYaM6C_vGkan5lTmOM9-S2lavGy8K10p9SLJ2V3j4WMDXD7FQX9TVT1fRSmY8f0IV01TKYWVHONJk2tnd1TBLqiZ2fgnv7HGuwRvR");
				    
			    	break;
			    default:
			    	break;
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
	
	private JSONArray getParentFCMToken(long groupId) {
		JSONArray fcmTokenArray = new JSONArray();
		String query = "select FcmToken from authorization where User in (select Username from student where Id in (select UserId from user_group where GroupId = " + groupId + "))";
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			if (rs.next()) {
				fcmTokenArray.put(rs.getString("FcmToken"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fcmTokenArray;
	}
	
	private JSONArray getTeacherFCMToken(long groupId) {
		JSONArray fcmTokenArray = new JSONArray();
		String query = "select FcmToken from authorization where User in (select Username from teacher where Id in (select UserId from user_group where GroupId = " + groupId + "))";
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			if (rs.next()) {
				fcmTokenArray.put(rs.getString("FcmToken"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fcmTokenArray;
	}
	
	private JSONArray getTeacherFCMToken(long groupId, long teacherId) {
		JSONArray fcmTokenArray = new JSONArray();
		String query = "select FcmToken from authorization where User in (select Username from teacher where Id in (select UserId from user_group where GroupId = " + groupId + "))";
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			if (rs.next()) {
				fcmTokenArray.put(rs.getString("FcmToken"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fcmTokenArray;
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
				message.setVideoUrl(rs.getString("VideoUrl"));
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
				+ "order by Id desc";
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
				message.setVideoUrl(rs.getString("VideoUrl"));
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
				+ "and Id<? order by Id desc";
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
				message.setVideoUrl(rs.getString("VideoUrl"));
				message.setCreatedAt(rs.getString("CreatedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<Message> getGroupMessagesAboveId(long groupId, long messageId) {
		String query = "select A.*, B.Name from message A, teacher B where GroupId=? and A.SenderId=B.Id and A.Id>? order by A.Id desc";
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
				message.setSenderName(rs.getString("Name"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setMessageType(rs.getString("MessageType"));
				message.setMessageBody(rs.getString("MessageBody"));
				message.setImageUrl(rs.getString("ImageUrl"));
				message.setVideoUrl(rs.getString("VideoUrl"));
				message.setCreatedAt(rs.getString("CreatedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<Message> getGroupMessages(long groupId) {
		String query = "select A.*, B.Name from message A, teacher B where GroupId=? and A.SenderId=B.Id order by A.Id desc";
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
				message.setSenderName(rs.getString("Name"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setMessageType(rs.getString("MessageType"));
				message.setMessageBody(rs.getString("MessageBody"));
				message.setImageUrl(rs.getString("ImageUrl"));
				message.setVideoUrl(rs.getString("VideoUrl"));
				message.setCreatedAt(rs.getString("CreatedAt"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<Message> getGroupMessagesFromId(long groupId, long messageId) {
		String query = "select A.*, B.Name from message A, teacher B where GroupId=? and A.SenderId=B.Id and A.Id<? order by A.Id desc";
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
				message.setSenderName(rs.getString("Name"));
				message.setRecipientId(rs.getLong("RecipientId"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				message.setGroupId(rs.getLong("GroupId"));
				message.setMessageType(rs.getString("MessageType"));
				message.setMessageBody(rs.getString("MessageBody"));
				message.setImageUrl(rs.getString("ImageUrl"));
				message.setVideoUrl(rs.getString("VideoUrl"));
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
