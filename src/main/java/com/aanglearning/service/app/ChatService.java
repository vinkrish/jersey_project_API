package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		String query = "insert into chat(StudentId, StudentName, ClassName, SectionName, TeacherId, TeacherName, CreatedBy, CreatorRole) "
				+ "values (?,?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, chat.getStudentId());
	    	preparedStatement.setString(2, chat.getStudentName());
	    	preparedStatement.setString(3, chat.getClassName());
	    	preparedStatement.setString(4, chat.getSectionName());
	    	preparedStatement.setLong(5, chat.getTeacherId());
	    	preparedStatement.setString(6, chat.getTeacherName());
	    	preparedStatement.setLong(7, chat.getCreatedBy());
	    	preparedStatement.setString(8, chat.getCreatorRole());
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
				chat.setStudentName(rs.getString("StudentName"));
				chat.setClassName(rs.getString("ClassName"));
				chat.setSectionName(rs.getString("SectionName"));
				chat.setTeacherId(rs.getLong("TeacherId"));
				chat.setTeacherName(rs.getString("TeacherName"));
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
				chat.setStudentName(rs.getString("StudentName"));
				chat.setClassName(rs.getString("ClassName"));
				chat.setSectionName(rs.getString("SectionName"));
				chat.setTeacherId(rs.getLong("TeacherId"));
				chat.setTeacherName(rs.getString("TeacherName"));
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
