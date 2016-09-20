package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.Section;

public class SectionService {
	Statement stmt = null;

	public SectionService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Section> getSectionList(long classId) {
		String query = "select * from section where ClassId = " + classId;
		List<Section> sectionList = new ArrayList<Section>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Section section = new Section();
				section.setId(rs.getLong("Id"));
				section.setSectionName(rs.getString("SectionName"));
				section.setClassId(rs.getLong("ClassId"));
				section.setTeacherId(rs.getLong("TeacherId"));
				sectionList.add(section);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sectionList;
	}
	
	public Section add(Section section) {
		try {
			String query = "insert into section(Id, SectionName, ClassId, TeacherId) "
					+ "values (" 
					+ section.getId() + ",'" 
					+ section.getSectionName() + "',"
					+ section.getClassId() + ","
					+ section.getTeacherId() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			section.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return section;
	}
	
	public void update(Section section) {
		try {
			String query = "update section set SectionName = '" + section.getSectionName() 
			+ "', TeacherId = " + section.getTeacherId() 
			+ " where Id=" + section.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long sectionId){
		try {
			String query = "delete from section where Id=" + sectionId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
