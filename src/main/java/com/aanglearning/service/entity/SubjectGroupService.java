package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.SubjectGroup;
import com.aanglearning.service.JDBC;

public class SubjectGroupService {
	Statement stmt;

	public SubjectGroupService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<SubjectGroup> getSubjectGroupList(long schoolId) {
		String query = "select * from subject_group where SchoolId = " + schoolId;
		List<SubjectGroup> subjectGroupList = new ArrayList<SubjectGroup>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				SubjectGroup subjectGroup = new SubjectGroup();
				subjectGroup.setId(rs.getLong("Id"));
				subjectGroup.setSchoolId(rs.getLong("SchoolId"));
				subjectGroup.setSubjectGroupName(rs.getString("SubjectGroupName"));
				subjectGroupList.add(subjectGroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectGroupList;
	}
	
	public SubjectGroup add(SubjectGroup subjectGroup){
		try {
			String query = "insert into subject_group(Id, SchoolId, SubjectGroupName) "
					+ "values (" 
					+ subjectGroup.getId() + ","
					+ subjectGroup.getSchoolId() + ",'"
					+ subjectGroup.getSubjectGroupName() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			subjectGroup.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectGroup;
	}
	
	public void update(SubjectGroup subjectGroup) {
		try {
			String query = "update subject_group set SubjectGroupName = '" + subjectGroup.getSubjectGroupName()  
			+ "' where Id=" + subjectGroup.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long subjectGroupId) {
		try {
			String query = "delete from subject_group where Id=" + subjectGroupId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
