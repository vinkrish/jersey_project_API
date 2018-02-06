package com.aanglearning.service.fee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Student;
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
	
	public Student update(Student feeStudent) {
		String query = "update student set Discount = ? where Id = ?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, feeStudent.getDiscount());
	    	preparedStatement.setLong(2, feeStudent.getId());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
		return feeStudent;
	}
	
	public List<Student> getStudentsFee(long sectionId) {
		String query = "select * from student where SectionId=? order by RollNo";
		List<Student> studentFees = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, sectionId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Student studentFee = new Student();
				studentFee.setId(rs.getInt("Id"));
				studentFee.setName(rs.getString("Name"));
				studentFee.setUsername(rs.getString("Username"));
				studentFee.setSchoolId(rs.getLong("SchoolId"));
				studentFee.setClassId(rs.getLong("ClassId"));
				studentFee.setSectionId(rs.getLong("SectionId"));
				studentFee.setRollNo(rs.getInt("RollNo"));
				studentFee.setFeePaid(rs.getInt("FeePaid"));
				studentFee.setDiscount(rs.getInt("Discount"));
				studentFees.add(studentFee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentFees;
	}

}
