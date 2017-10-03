package com.aanglearning.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.Customer;

public class CustomerService {
	
	Connection connection;
	
	public CustomerService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Customer add(Customer customer) {
		String query = "insert into customer(ClientId, RefId, Name, Mobile, Date, Place, CreatedTime) "
				+ "values (?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    preparedStatement.setLong(1, customer.getClientId());
	    	preparedStatement.setString(2, customer.getRefId());
	    	preparedStatement.setString(3, customer.getName());
	    	preparedStatement.setString(4, customer.getMobile());
	    	preparedStatement.setString(5, customer.getDate());
	    	preparedStatement.setString(6, customer.getPlace());
	    	preparedStatement.setLong(7, customer.getCreatedTime());
	    	preparedStatement.executeUpdate();
	    	ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			customer.setId(pk);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return customer;
	}
	
	private void addDeletedCustomer(List<Customer> customers) {
		String query = "insert into customer(ClientId, RefId, Name, Mobile, Date, Place, DeletedTime) "
				+ "values (?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(Customer customer: customers) {
		    	preparedStatement.setLong(1, customer.getClientId());
		    	preparedStatement.setString(2, customer.getRefId());
		    	preparedStatement.setString(3, customer.getName());
		    	preparedStatement.setString(4, customer.getMobile());
		    	preparedStatement.setString(5, customer.getDate());
		    	preparedStatement.setString(6, customer.getPlace());
		    	preparedStatement.setLong(7, System.currentTimeMillis());
		    	preparedStatement.executeUpdate();
		    }
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public List<Customer> getCustomers(long clientId) {
		String query = "select * from customer where ClientId = ? order by Id desc LIMIT 100";
		List<Customer> customers = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, clientId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Customer customer = new Customer();
				customer.setId(rs.getLong("Id"));
				customer.setClientId(rs.getLong("ClientId"));
				customer.setRefId(rs.getString("RefId"));
				customer.setName(rs.getString("Name"));
				customer.setMobile(rs.getString("Mobile"));
				customer.setDate(rs.getString("Date"));
				customer.setPlace(rs.getString("Place"));
				customer.setCreatedTime(rs.getLong("CreatedTime"));
				customers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public List<Customer> getCustomersFromId(long clientId, long id) {
		String query = "select * from customer where ClientId = ? and Id < ? order by Id desc LIMIT 100";
		List<Customer> customers = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, clientId);
			preparedStatement.setLong(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Customer customer = new Customer();
				customer.setId(rs.getLong("Id"));
				customer.setClientId(rs.getLong("ClientId"));
				customer.setRefId(rs.getString("RefId"));
				customer.setName(rs.getString("Name"));
				customer.setMobile(rs.getString("Mobile"));
				customer.setDate(rs.getString("Date"));
				customer.setPlace(rs.getString("Place"));
				customer.setCreatedTime(rs.getLong("CreatedTime"));
				customers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public void deleteCustomers(List<Customer> customers) {
		addDeletedCustomer(customers);
		String query = "delete from customer where Id=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			for (Customer customer : customers) {
				preparedStatement.setLong(1, customer.getId());
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
