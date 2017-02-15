package com.aanglearning.service.cce;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.cce.CceSectionHeading;
import com.aanglearning.service.JDBC;

public class CceSectionHeadingService {
	Statement stmt = null;

	public CceSectionHeadingService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CceSectionHeading> getCceSectionHeadings(long coScholasticId) {
		String query = "select * from cce_section_heading where CoScholasticId = " + coScholasticId;
		List<CceSectionHeading> coscholastics = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				CceSectionHeading sectionHeading = new CceSectionHeading();
				sectionHeading.setId(rs.getLong("Id"));
				sectionHeading.setName(rs.getString("Name"));
				sectionHeading.setCoscholasticId(rs.getLong("CoScholasticId"));
				coscholastics.add(sectionHeading);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coscholastics;
	}
	
	public CceSectionHeading add(CceSectionHeading sectionHeading) {
		try {
			String query = "insert into cce_section_heading(Id, Name, CoScholasticId) "
					+ "values (" 
					+ sectionHeading.getId() + ",'" 
					+ sectionHeading.getName() + "',"
					+ sectionHeading.getCoscholasticId() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			sectionHeading.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sectionHeading;
	}
	
	public void update(CceSectionHeading sectionHeading) {
		try {
			String query = "update cce_section_heading set Name = '" + sectionHeading.getName()
			+ "'  where Id=" + sectionHeading.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from cce_section_heading where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
