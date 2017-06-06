package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.SubjectGroupSubject;
import com.aanglearning.service.DatabaseUtil;

public class SubjectGroupSubjectService {
	Statement stmt;

	public SubjectGroupSubjectService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<SubjectGroupSubject> getSubjectGroupSubjects(long subjectGroupId) {
		String query = "select * from subject_group_subject where SubjectGroupId = " + subjectGroupId;
		List<SubjectGroupSubject> subjectGroupSubjects = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				SubjectGroupSubject csg = new SubjectGroupSubject();
				csg.setId(rs.getLong("Id"));
				csg.setSubjectGroupId(rs.getLong("SubjectGroupId"));
				csg.setSubjectId(rs.getLong("SubjectId"));
				csg.setSubjectName(rs.getString("SubjectName"));
				subjectGroupSubjects.add(csg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectGroupSubjects;
	}
	
	public SubjectGroupSubject add(SubjectGroupSubject sgs) {
		try {
			String query = "insert into subject_group_subject(Id, SubjectGroupId, SubjectId, SubjectName) "
					+ "values ("
					+ sgs.getId() + "," 
					+ sgs.getSubjectGroupId() + ","
					+ sgs.getSubjectId() + ",'" 
					+ sgs.getSubjectName() + "')"; 
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			sgs.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sgs;
	}
	
	public void delete(long id){
		try {
			String query = "delete from subject_group_subject where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
