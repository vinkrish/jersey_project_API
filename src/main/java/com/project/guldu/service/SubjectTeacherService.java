package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.SubjectTeacher;

public class SubjectTeacherService {
	Statement stmt = null;

	public SubjectTeacherService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<SubjectTeacher> getSubjectTeacher(long sectionId) {
		String query = "select * from subject_teacher where SectionId = " + sectionId;
		List<SubjectTeacher> classList = new ArrayList<SubjectTeacher>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				SubjectTeacher subjectTeacher = new SubjectTeacher();
				subjectTeacher.setId(rs.getLong("Id"));
				subjectTeacher.setSectionId(rs.getLong("SectionId"));
				subjectTeacher.setSubjectId(rs.getLong("SubjectId"));
				subjectTeacher.setTeacherId(rs.getLong("TeacherId"));
				classList.add(subjectTeacher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classList;
	}
	
	public void addSubjectTeacher(String subjectTeacherStr) {
		JSONArray subjectTeacherArray = new JSONArray(subjectTeacherStr);
		for (int i = 0; i < subjectTeacherArray.length(); i++) {
			JSONObject subjectTeacherJson = subjectTeacherArray.getJSONObject(i);
			Gson gson = new Gson();
			SubjectTeacher subjectTeacher = gson.fromJson(subjectTeacherJson.toString(), SubjectTeacher.class);
			try {
			String query = "insert into subject_teacher(Id, SectionId, SubjectId, TeacherId) "
						+ "values (" 
						+ subjectTeacher.getId() + "," 
						+ subjectTeacher.getSectionId() + ","
						+ subjectTeacher.getSubjectId() + ","
						+ subjectTeacher.getTeacherId() + ")";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public SubjectTeacher add(SubjectTeacher subjectTeacher) {
		try {
			String query = "insert into subject_teacher(Id, SectionId, SubjectId, TeacherId) "
					+ "values (" 
					+ subjectTeacher.getId() + "," 
					+ subjectTeacher.getSectionId() + ","
					+ subjectTeacher.getSubjectId() + ","
					+ subjectTeacher.getTeacherId() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			subjectTeacher.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectTeacher;
	}
	
	public void update(SubjectTeacher subjectTeacher) {
		try {
			String query = "update subject_teacher set SectionId = " + subjectTeacher.getSectionId() 
			+ ", TeacherId = " + subjectTeacher.getTeacherId() 
			+ ", SubjectId = " + subjectTeacher.getSubjectId() 
			+ " where Id=" + subjectTeacher.getId();
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from subject_teacher where Id=" + id;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
