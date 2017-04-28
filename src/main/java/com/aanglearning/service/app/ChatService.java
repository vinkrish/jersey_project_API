package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.Chat;
import com.aanglearning.service.JDBC;

public class ChatService {
	Connection connection;
	
	public ChatService() {
		connection = JDBC.getConnection();
	}
	
	public Chat add(Chat chat) {
		String query = "insert into chat(StudentId, TeacherId, CreatedBy, CreatorRole) values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setLong(1, chat.getStudentId());
	    	preparedStatement.setLong(2, chat.getTeacherId());
	    	preparedStatement.setLong(3, chat.getCreatedBy());
	    	preparedStatement.setString(4, chat.getCreatorRole());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			chat.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return chat;
	}
	
	public List<Chat> getChatParents(long teacherId) {
		String query = "select * from chat where TeacherId=? and CreatorRole='teacher' order by Id";
		List<Chat> chats = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, teacherId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Chat chat = new Chat();
				chat.setId(rs.getLong("Id"));
				chat.setStudentId(rs.getLong("StudentId"));
				chat.setTeacherId(rs.getLong("TeacherId"));
				chat.setCreatedBy(rs.getLong("CreatedBy"));
				chat.setCreatorRole(rs.getString("CreatorRole"));
				chats.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chats;
	}
	
	public List<Chat> getChatTeachers(long studentId) {
		String query = "select * from chat where StudentId=? and CreatorRole='parent' order by Id";
		List<Chat> chats = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, studentId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Chat chat = new Chat();
				chat.setId(rs.getLong("Id"));
				chat.setStudentId(rs.getLong("StudentId"));
				chat.setTeacherId(rs.getLong("TeacherId"));
				chat.setCreatedBy(rs.getLong("CreatedBy"));
				chat.setCreatorRole(rs.getString("CreatorRole"));
				chats.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chats;
	}
	
	public void delete(long id) {
		String query = "delete from chat where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
