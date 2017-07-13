package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aanglearning.model.entity.Section;
import com.aanglearning.service.DatabaseUtil;

public class SectionService {
	Statement stmt;

	public SectionService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Section> getSectionList(long classId) {
		String query = "select * from section where ClassId = " + classId;
		return getSections(query);
	}
	
	public List<Section> getSectionTeacherSections(long classId, long teacherId) {
		String query = "select * from section where ClassId = " + classId + " and TeacherId = " + teacherId;
		return getSections(query);
	}
	
	public List<Section> getSubjectTeacherSections(long classId, long teacherId) {
		String query1 = "select * from section where Id in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId) and ClassId=" + classId;
		String query2 = "select * from section where ClassId = " + classId + " and TeacherId = " + teacherId;
		String query3 = "select * from section where ClassId in (select Id from class where TeacherId = " + teacherId + ")";
		Set<Section> set = new HashSet<Section>(getSections(query1));
		set.addAll(getSections(query2));
		set.addAll(getSections(query3));
		return new ArrayList<Section>(set);
	}
	
	public List<Section> getSections(String query) {
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
