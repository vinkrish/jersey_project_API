package com.aanglearning.service.cce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.cce.CceStudentProfile;
import com.aanglearning.service.JDBC;

public class CceStudentProfileService {
	Connection connection = null;

	public CceStudentProfileService() {
		connection = JDBC.getConnection();
	}

	public List<CceStudentProfile> getCceStudentProfiles(long sectionId, int term) {
		String query = "select * from cce_student_profile where SectionId=? and Term=?";
		List<CceStudentProfile> cceProfiles = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, sectionId);
	    	preparedStatement.setInt(2, term);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				CceStudentProfile profile = new CceStudentProfile();
				profile.setId(rs.getLong("Id"));
				profile.setSectionId(rs.getLong("SectionId"));
				profile.setStudentId(rs.getLong("StudentId"));
				profile.setTerm(rs.getInt("Term"));
				profile.setFromDate(rs.getString("FromDate"));
				profile.setToDate(rs.getString("ToDate"));
				profile.setTotalDays(rs.getInt("TotalDays"));
				profile.setDaysAttended(rs.getFloat("DaysAttended"));
				profile.setHeight(rs.getFloat("Height"));
				profile.setWeight(rs.getFloat("Weight"));
				profile.setHealthStatus(rs.getString("HealthStatus"));
				profile.setBloodGroup(rs.getString("BloodGroup"));
				profile.setVisionLeft(rs.getString("VisionLeft"));
				profile.setVisionRight(rs.getString("VisionRight"));
				profile.setAilment(rs.getString("Ailment"));
				profile.setOralHygiene(rs.getString("OralHygiene"));
				cceProfiles.add(profile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cceProfiles;
	}

	public void add(List<CceStudentProfile> cceProfiles) {
		String query = "insert into cce_student_profile(Id, SectionId, StudentId, Term, FromDate, ToDate, TotalDays, DaysAttended ,"
				+ " Height, Weight, HealthStatus, BloodGroup, VisionLeft, VisionRight, Ailment, OralHygiene)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement stmt = connection.prepareStatement(query);
		    for(CceStudentProfile profile: cceProfiles) {
		    	stmt.setLong(1, profile.getId());
		    	stmt.setLong(2, profile.getSectionId());
		    	stmt.setLong(3, profile.getStudentId());
		    	stmt.setInt(4, profile.getTerm());
		    	stmt.setString(5, profile.getFromDate());
		    	stmt.setString(6, profile.getToDate());
		    	stmt.setInt(7, profile.getTotalDays());
		    	stmt.setFloat(8, profile.getDaysAttended());
		    	stmt.setFloat(9, profile.getHeight());
		    	stmt.setFloat(10, profile.getWeight());
		    	stmt.setString(11, profile.getHealthStatus());
		    	stmt.setString(12, profile.getBloodGroup());
		    	stmt.setString(13, profile.getVisionLeft());
		    	stmt.setString(14, profile.getVisionRight());
		    	stmt.setString(15, profile.getAilment());
		    	stmt.setString(16, profile.getOralHygiene());
		    	stmt.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void update(List<CceStudentProfile> cceProfiles) {
		String query = "update cce_student_profile set FromDate=?, ToDate=?, TotalDays=?, DaysAttended=?,"
				+ " Height=?, Weight=?, HealthStatus=?, BloodGroup=?, VisionLeft=?, VisionRight=?, Ailment=?, OralHygiene=?"
				+ " where Id=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement stmt = connection.prepareStatement(query);
		    for(CceStudentProfile profile: cceProfiles) {
		    	stmt.setString(1, profile.getFromDate());
		    	stmt.setString(2, profile.getToDate());
		    	stmt.setInt(3, profile.getTotalDays());
		    	stmt.setFloat(4, profile.getDaysAttended());
		    	stmt.setFloat(5, profile.getHeight());
		    	stmt.setFloat(6, profile.getWeight());
		    	stmt.setString(7, profile.getHealthStatus());
		    	stmt.setString(8, profile.getBloodGroup());
		    	stmt.setString(9, profile.getVisionLeft());
		    	stmt.setString(10, profile.getVisionRight());
		    	stmt.setString(11, profile.getAilment());
		    	stmt.setString(12, profile.getOralHygiene());
		    	stmt.setLong(13, profile.getId());
		    	stmt.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void delete(long sectionId, int term) {
		String query = "delete from cce_student_profile where SectionId=? and term=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, sectionId);
		    	preparedStatement.setLong(2, term);
		    	preparedStatement.executeUpdate();
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
