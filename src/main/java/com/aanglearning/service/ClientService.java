package com.aanglearning.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.Client;

public class ClientService {
	Connection connection;
	
	public ClientService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Client add(Client client) {
		String query = "insert into client(Name, Mobile, Email, Password, SmsCredentials, SmsCount) "
				+ "values (?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, client.getName());
	    	preparedStatement.setString(2, client.getMobile());
	    	preparedStatement.setString(3, client.getEmail());
	    	preparedStatement.setString(4, client.getPassword());
	    	preparedStatement.setInt(5, client.getSmsCredentials());
	    	preparedStatement.setInt(6, client.getSmsCount());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			client.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return client;
	}
	
	public List<Client> getClients() {
		String query = "select * from client";
		List<Client> clients = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Client client = new Client();
				client.setId(rs.getLong("Id"));
				client.setName(rs.getString("Name"));
				client.setMobile(rs.getString("Mobile"));
				client.setEmail(rs.getString("Email"));
				client.setPassword(rs.getString("Password"));
				client.setSmsCredentials(rs.getInt("SmsCredentials"));
				client.setSmsCount(rs.getInt("SmsCount"));
				clients.add(client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}
	
	public Client getClient(long id) {
		String query = "select * from client where Id = ?";
		Client client = new Client();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				client.setId(rs.getLong("Id"));
				client.setName(rs.getString("Name"));
				client.setMobile(rs.getString("Mobile"));
				client.setEmail(rs.getString("Email"));
				client.setPassword(rs.getString("Password"));
				client.setSmsCredentials(rs.getInt("SmsCredentials"));
				client.setSmsCount(rs.getInt("SmsCount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	public void delete(long id) {
		String query = "delete from client where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	

}
