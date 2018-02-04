package com.aanglearning.service.fee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.fee.FeeStudent;
import com.aanglearning.service.DatabaseUtil;

public class FeeStudentService {
	
	Connection connection;
	
	public FeeStudentService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public FeeStudent update(FeeStudent feeStudent) {
		String query = "update fee_student set Discount = ? where StudentId = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, feeStudent.getDiscount());
	    	preparedStatement.setLong(2, feeStudent.getStudentId());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return feeStudent;
	}
	
	public List<FeeStudent> getStudentsFee(long sectionId) {
		String query = "select * from fee_student where SectionId=? order by RollNo";
		List<FeeStudent> studentFees = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, sectionId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				FeeStudent studentFee = new FeeStudent();
				studentFee.setId(rs.getInt("Id"));
				studentFee.setStudentId(rs.getLong("StudentId"));
				studentFee.setStudentName(rs.getString("StudentName"));
				studentFee.setSchoolId(rs.getLong("SchoolId"));
				studentFee.setClassId(rs.getLong("ClassId"));
				studentFee.setSectionId(rs.getLong("SectionId"));
				studentFee.setRollNo(rs.getInt("RollNo"));
				studentFee.setAmount(rs.getInt("Amount"));
				studentFee.setDiscount(rs.getInt("Discount"));
				studentFees.add(studentFee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentFees;
	}

}
