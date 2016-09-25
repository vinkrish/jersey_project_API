package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.Sliptest;

public class SliptestService {
	Statement stmt = null;

	public SliptestService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Sliptest> getSliptests(long sectionId, long subjectId) {
		String query = "select * from sliptest where SectionID = " + sectionId + " and SubjectId = " + subjectId;
		List<Sliptest> sliptests = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Sliptest sliptest = new Sliptest();
				sliptest.setId(rs.getLong("Id"));
				sliptest.setSectionId(rs.getLong("SectionId"));
				sliptest.setSubjectId(rs.getLong("SubjectId"));
				sliptest.setSliptestName(rs.getString("SliptestName"));
				sliptest.setPortionIds(rs.getString("PortionIds"));
				sliptest.setExtraPortion(rs.getString("ExtraPortion"));
				sliptest.setMaximumMark(rs.getFloat("MaximumMark"));
				sliptest.setAverage(rs.getFloat("Average"));
				sliptest.setTestDate(rs.getString("TestDate"));
				sliptest.setSubmissionDate(rs.getString("SubmissionDate"));
				sliptests.add(sliptest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sliptests;
	}
	
	public Sliptest add(Sliptest sliptest) {
		try {
			String query = "insert into sliptest(Id, SectionId, SubjectId, SliptestName, PortionIds, "
					+ "ExtraPortion, MaximumMark, Average, TestDate, SubmissionDate) "
					+ "values (" 
					+ sliptest.getId() + ","
					+ sliptest.getSectionId() + ","
					+ sliptest.getSubjectId() + ",'" 
					+ sliptest.getSliptestName() + "','"
					+ sliptest.getPortionIds() + "','"
					+ sliptest.getExtraPortion() + "',"
					+ sliptest.getMaximumMark() + ","
					+ sliptest.getAverage() + ",'"
					+ sliptest.getTestDate() + "','"
					+ sliptest.getSubmissionDate() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			sliptest.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sliptest;
	}
	
	public void update(Sliptest sliptest) {
		try {
			String query = "update sliptest set SliptestName = '" + sliptest.getSliptestName()
			+ "', PortionIds = '" + sliptest.getPortionIds()
			+ "', ExtraPortion = '" + sliptest.getExtraPortion()
			+ "', MaximumMark = " + sliptest.getMaximumMark()
			+ ", Average = " + sliptest.getAverage()
			+ ", TestDate = '" + sliptest.getTestDate()
			+ "', SubmissionDate = '" + sliptest.getSubmissionDate()
			+ "' where Id=" + sliptest.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from sliptest where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
