package com.aanglearning.service.fee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.fee.FeeClass;
import com.aanglearning.service.DatabaseUtil;

public class FeeClassService {
	
	Connection connection;
	
	public FeeClassService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public FeeClass update(FeeClass feeClass) {
		String query = "update fee_class set Amount = ?, Type = ? where ClassId = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, feeClass.getAmount());
	    	preparedStatement.setString(2, feeClass.getType());
	    	preparedStatement.setLong(3, feeClass.getClassId());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return feeClass;
	}
	
	public List<FeeClass> getClassFees(long schoolId) {
		String query = "select * from fee_class where SchoolId=? order by ClassId";
		List<FeeClass> feeClasses = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, schoolId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				FeeClass feeClass = new FeeClass();
				feeClass.setId(rs.getInt("Id"));
				feeClass.setClassId(rs.getLong("ClassId"));
				feeClass.setClassName(rs.getString("ClassName"));
				feeClass.setSchoolId(rs.getLong("SchoolId"));
				feeClass.setAmount(rs.getInt("Amount"));
				feeClass.setType(rs.getString("Type"));
				feeClasses.add(feeClass);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return feeClasses;
	}
}
