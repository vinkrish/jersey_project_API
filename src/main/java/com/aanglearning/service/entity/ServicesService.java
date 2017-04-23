package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.aanglearning.model.entity.Service;
import com.aanglearning.service.JDBC;

public class ServicesService {
	Statement stmt;

	public ServicesService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Service add(Service service) {
		try {
			String query = "insert into service(Id, SchoolId, IsMessage, IsSms, IsAttendance, IsHomework, IsAttendanceSms, IsHomeworkSms) "
					+ "values ("
					+ service.getId() + "," 
					+ service.getSchoolId() + ","
					+ service.getIsMessage() + ","
					+ service.getIsSms() + ","
					+ service.getIsAttendance() + ","
					+ service.getIsAttendanceSms() + ","
					+ service.getIsHomework() + ","
					+ service.getIsHomeworkSms() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) pk = rs.getLong(1);
			service.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return service;
	}
	
	public Service getService(long schoolId) {
		String query = "select * from service where SchoolId = " + schoolId ;
		Service service = new Service();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				service.setId(rs.getLong("Id"));
				service.setSchoolId(rs.getLong("SchoolId"));
				service.setIsMessage(rs.getBoolean("IsMessage"));
				service.setIsSms(rs.getBoolean("IsSms"));
				service.setIsAttendance(rs.getBoolean("IsAttendance"));
				service.setIsAttendanceSms(rs.getBoolean("IsAttendanceSms"));
				service.setIsHomework(rs.getBoolean("IsHomework"));
				service.setIsHomeworkSms(rs.getBoolean("IsHomeworkSms"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return service;
	}
	
	public void update(Service service) {
		try {
			String query = "update service set"
					+ " IsMessage = " + service.getIsMessage()
					+ ", IsSms = " + service.getIsSms()
					+ ", IsAttendance = " + service.getIsAttendance()
					+ ", IsAttendanceSms = " + service.getIsAttendanceSms()
					+ ", IsHomework = " + service.getIsHomework()
					+ ", IsHomeworkSms = " + service.getIsHomeworkSms()
					+ " where Id = " + service.getId();
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from service where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
