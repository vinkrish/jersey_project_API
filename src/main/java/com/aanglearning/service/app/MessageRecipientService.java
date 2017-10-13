package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.MessageRecipient;
import com.aanglearning.service.DatabaseUtil;

public class MessageRecipientService {
	Connection connection;

	public MessageRecipientService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<MessageRecipient> add(List<MessageRecipient> mrList) {
		String query = "insert into message_recipient(RecipientId, RecipientName, Role, GroupId, MessageId, IsRead, ReadAt) "
				+ "values (?,?,?,?,?,?,?)";
		for (MessageRecipient mr : mrList) {
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setLong(1, mr.getRecipientId());
				preparedStatement.setString(2, mr.getRecipientName());
				preparedStatement.setString(3, mr.getRole());
				preparedStatement.setLong(4, mr.getGroupId());
				preparedStatement.setLong(5, mr.getMessageId());
				preparedStatement.setBoolean(6, mr.getIsRead());
				preparedStatement.setLong(7, mr.getReadAt());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
			    long pk = 0;
				if (rs.next()){
				    pk = rs.getLong(1);
				}
				mr.setId(pk);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mrList;
	}

	public List<MessageRecipient> getMessageRecipients(long groupId, long groupMessageId) {
		String query1 = "select * from message_recipient where GroupId = ? and MessageId = ?";
		List<MessageRecipient> mrList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query1);
			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, groupMessageId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				MessageRecipient mr = new MessageRecipient();
				mr.setId(rs.getLong("Id"));
				mr.setRecipientId(rs.getLong("RecipientId"));
				mr.setRecipientName(rs.getString("RecipientName"));
				mr.setRole(rs.getString("Role"));
				mr.setGroupId(rs.getLong("GroupId"));
				mr.setMessageId(rs.getLong("MessageId"));
				mr.setIsRead(rs.getBoolean("IsRead"));
				mr.setReadAt(rs.getLong("ReadAt"));
				mrList.add(mr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query2 = "select B.Id as Id, B.Name as Name from user_group A, student B "
				+ "where A.GroupId=? and A.Role='student' and A.UserId=B.Id and B.Id not in "
				+ "(select RecipientId from message_recipient where groupId = ? and MessageId = ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query2);
			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, groupId);
			preparedStatement.setLong(3, groupMessageId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				MessageRecipient mr = new MessageRecipient();
				mr.setRecipientId(rs.getLong("Id"));
				mr.setRecipientName(rs.getString("Name"));
				mr.setRole("student");
				mr.setGroupId(groupId);
				mr.setIsRead(false);
				mrList.add(mr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mrList;
	}
	
	public List<MessageRecipient> getSchoolRecipients(long groupId, long groupMessageId) {
		String query1 = "select * from message_recipient where GroupId = ? and MessageId = ? order by Id desc limit 50";
		List<MessageRecipient> mrList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query1);
			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, groupMessageId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				MessageRecipient mr = new MessageRecipient();
				mr.setId(rs.getLong("Id"));
				mr.setRecipientId(rs.getLong("RecipientId"));
				mr.setRecipientName(rs.getString("RecipientName"));
				mr.setRole(rs.getString("Role"));
				mr.setGroupId(rs.getLong("GroupId"));
				mr.setMessageId(rs.getLong("MessageId"));
				mr.setIsRead(rs.getBoolean("IsRead"));
				mr.setReadAt(rs.getLong("ReadAt"));
				mrList.add(mr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mrList;
	}
	
	public List<MessageRecipient> getSchoolRecipientsFromId(long groupId, long groupMessageId, long id) {
		String query1 = "select * from message_recipient where GroupId = ? and MessageId = ? and Id < ? order by Id desc limit 50";
		List<MessageRecipient> mrList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query1);
			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, groupMessageId);
			preparedStatement.setLong(3, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				MessageRecipient mr = new MessageRecipient();
				mr.setId(rs.getLong("Id"));
				mr.setRecipientId(rs.getLong("RecipientId"));
				mr.setRecipientName(rs.getString("RecipientName"));
				mr.setRole(rs.getString("Role"));
				mr.setGroupId(rs.getLong("GroupId"));
				mr.setMessageId(rs.getLong("MessageId"));
				mr.setIsRead(rs.getBoolean("IsRead"));
				mr.setReadAt(rs.getLong("ReadAt"));
				mrList.add(mr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mrList;
	}
	
	public List<MessageRecipient> getAllMessageRecipients(long recipientId) {
		String query = "select * from message_recipient where RecipientId = ?";
		List<MessageRecipient> mrList = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, recipientId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				MessageRecipient mr = new MessageRecipient();
				mr.setId(rs.getLong("Id"));
				mr.setRecipientId(rs.getLong("RecipientId"));
				mr.setRecipientName(rs.getString("RecipientName"));
				mr.setRole(rs.getString("Role"));
				mr.setGroupId(rs.getLong("GroupId"));
				mr.setMessageId(rs.getLong("MessageId"));
				mr.setIsRead(rs.getBoolean("IsRead"));
				mr.setReadAt(rs.getLong("ReadAt"));
				mrList.add(mr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mrList;
	}
}
