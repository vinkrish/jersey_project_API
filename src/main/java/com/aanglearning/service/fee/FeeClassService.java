package com.aanglearning.service.fee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Clas;
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
	
	public Clas update(Clas clas) {
		String query = "update class set FeeAmount = ?, FeeType = ? where Id = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, clas.getFeeAmount());
	    	preparedStatement.setString(2, clas.getFeeType());
	    	preparedStatement.setLong(3, clas.getId());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return clas;
	}
	
	public List<Clas> getClassFees(long schoolId) {
		String query = "select * from class where SchoolId=? order by Id";
		List<Clas> feeClasses = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, schoolId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Clas feeClass = new Clas();
				feeClass.setId(rs.getInt("Id"));
				feeClass.setClassName(rs.getString("ClassName"));
				feeClass.setSchoolId(rs.getLong("SchoolId"));
				feeClass.setFeeAmount(rs.getInt("FeeAmount"));
				feeClass.setFeeType(rs.getString("FeeType"));
				feeClasses.add(feeClass);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return feeClasses;
	}
}
