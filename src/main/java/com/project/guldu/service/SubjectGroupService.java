package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.SubjectGroup;

public class SubjectGroupService {
	Statement stmt = null;

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
	
	public void addSubjectGroup(String subjectGroupStr) {
		JSONArray subjectGroupArray = new JSONArray(subjectGroupStr);
		for (int i = 0; i < subjectGroupArray.length(); i++) {
			JSONObject subjectGroupJson = subjectGroupArray.getJSONObject(i);
			Gson gson = new Gson();
			SubjectGroup subjectGroup = gson.fromJson(subjectGroupJson.toString(), SubjectGroup.class);
			try {
				String query = "insert into subject_group(Id, SubjectGroupName) "
						+ "values (" 
						+ subjectGroup.getId() + ","
						+ subjectGroup.getSchoolId() + ",'"
						+ subjectGroup.getSubjectGroupName() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long subjectGroupId) {
		try {
			String query = "delete from subject_group where Id=" + subjectGroupId;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
