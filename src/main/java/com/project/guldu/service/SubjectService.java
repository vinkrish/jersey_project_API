package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.Subject;

public class SubjectService {
	Statement stmt = null;

	public SubjectService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Subject> getSubjectList(long schoolId) {
		String query ="select * from subject where SchoolId = " + schoolId;
		List<Subject> subjectList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Subject subject = new Subject();
				subject.setId(rs.getLong("Id"));
				subject.setSchoolId(rs.getLong("SchoolId"));
				subject.setSubjectName(rs.getString("SubjectName"));
				subject.setPartitionType(rs.getInt("partitionType"));
				subject.setTheorySubjectId(rs.getLong("TheorySubjectId"));
				subject.setPracticalSubjectId(rs.getLong("PracticalSubjectId"));
				subjectList.add(subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectList;
	}
	
	public void addSubject(String subjectStr){
		JSONArray subjectArray = new JSONArray(subjectStr);
		for (int i = 0; i < subjectArray.length(); i++) {
			JSONObject subjectJson = subjectArray.getJSONObject(i);
			Gson gson = new Gson();
			Subject subject = gson.fromJson(subjectJson.toString(), Subject.class);
			try {
				String query = "insert into subject(Id, SchoolId, SubjectName, PartitionType, TheorySubjectId, PracticalSubjectId) "
						+ "values ("
						+ subject.getId() + ","
						+ subject.getSchoolId() + ",'"
						+ subject.getSubjectName() + "',"
						+ subject.getPartitionType() + ","
						+ subject.getTheorySubjectId() + ","
						+ subject.getPracticalSubjectId() + ")";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Subject add(Subject subject){
		try {
			String query = "insert into subject(Id, SchoolId, SubjectName, PartitionType, TheorySubjectId, PracticalSubjectId) "
					+ "values ("
					+ subject.getId() + ","
					+ subject.getSchoolId() + ",'"
					+ subject.getSubjectName() + "',"
					+ subject.getPartitionType() + ","
					+ subject.getTheorySubjectId() + ","
					+ subject.getPracticalSubjectId() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			subject.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subject;
	}
	
	public void update(Subject subject) {
		try {
			String query = "update subject set SubjectName = '" + subject.getSubjectName() 
			+"', PartitionType = "+ subject.getPartitionType() 
			+ ", TheorySubjectId = " + subject.getTheorySubjectId() 
			+ ", PracticalSubjectId = " + subject.getPracticalSubjectId() + " where Id=" + subject.getId();
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long subjectId) {
		try {
			String query = "delete from subject where Id=" + subjectId;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
