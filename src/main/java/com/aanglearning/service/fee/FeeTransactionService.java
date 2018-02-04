package com.aanglearning.service.fee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.fee.FeeTransaction;
import com.aanglearning.service.DatabaseUtil;

public class FeeTransactionService {
	
	Connection connection;
	
	public FeeTransactionService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public FeeTransaction saveStudentFee(FeeTransaction feeTransaction) {
		String query = "insert into fee_transaction(StudentId, Paid, PaidOn, Type, Description) values(?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, feeTransaction.getStudentId());
			preparedStatement.setInt(2, feeTransaction.getPaid());
			preparedStatement.setString(3, feeTransaction.getPaidOn());
			preparedStatement.setString(4, feeTransaction.getType());
			preparedStatement.setString(5, feeTransaction.getDescription());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
		    long pk = 0;
			if (rs.next()){
			    pk = rs.getLong(1);
			}
			feeTransaction.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateStudentFee(feeTransaction.getStudentId(), feeTransaction.getPaid(), "add");
		return feeTransaction;
	}
	
	public List<FeeTransaction> getStudentFee(long studentId) {
		String query = "select * from fee_transaction where StudentId=?";
		List<FeeTransaction> studentFees = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, studentId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				FeeTransaction studentFee = new FeeTransaction();
				studentFee.setId(rs.getInt("Id"));
				studentFee.setStudentId(rs.getLong("StudentId"));
				studentFee.setPaid(rs.getInt("Paid"));
				studentFee.setPaidOn(rs.getString("PaidOn"));
				studentFee.setType(rs.getString("Type"));
				studentFee.setDescription(rs.getString("Description"));
				studentFees.add(studentFee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentFees;
	}
	
	public void updateStudentFee(long studentId, int amount, String status) {
		String query = "select * from fee_student where StudentId=?";
		int currentPaid = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, studentId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				currentPaid = rs.getInt("Amount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(status.equals("add")) {
			currentPaid += amount;
		} else if(currentPaid!= 0 && currentPaid >= amount) {
			currentPaid -= amount;
		}
		
		String sql = "update fee_student set Amount = ? where StudentId = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(sql);
	    	preparedStatement.setInt(1, currentPaid);
	    	preparedStatement.setLong(2, studentId);
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void delete(long id) {
		String query = "delete from fee_transaction where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setLong(1, id);
		    preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
		
		String sql = "select * from fee_transaction where Id=?";
		FeeTransaction feeTransaction = new FeeTransaction();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				feeTransaction.setId(rs.getInt("Id"));
				feeTransaction.setStudentId(rs.getLong("StudentId"));
				feeTransaction.setPaid(rs.getInt("Paid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		updateStudentFee(feeTransaction.getStudentId(), feeTransaction.getPaid(), "minus");
	}

}
