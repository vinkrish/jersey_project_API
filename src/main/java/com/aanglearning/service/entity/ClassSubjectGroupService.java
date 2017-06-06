package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.ClassSubjectGroup;
import com.aanglearning.service.DatabaseUtil;

public class ClassSubjectGroupService {
	Statement stmt;

	public ClassSubjectGroupService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ClassSubjectGroup> getClassSubjectGroups(long classId) {
		String query = "select * from class_subject_group where ClassId = " + classId;
		List<ClassSubjectGroup> classSubjectGroups = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				ClassSubjectGroup csg = new ClassSubjectGroup();
				csg.setId(rs.getLong("Id"));
				csg.setClassId(rs.getLong("ClassId"));
				csg.setSubjectGroupId(rs.getLong("SubjectGroupId"));
				csg.setSubjectGroupName(rs.getString("SubjectGroupName"));
				classSubjectGroups.add(csg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classSubjectGroups;
	}
	
	public ClassSubjectGroup add(ClassSubjectGroup csg) {
		try {
			String query = "insert into class_subject_group(Id, ClassId, SubjectGroupId, SubjectGroupName) "
					+ "values ("
					+ csg.getId() + "," 
					+ csg.getClassId() + ","
					+ csg.getSubjectGroupId() + ",'" 
					+ csg.getSubjectGroupName() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			csg.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return csg;
	}
	
	public void update(ClassSubjectGroup csg) {
		try {
			String query = "update class_subject_group set ClassId = "+ csg.getClassId() 
			+ ", SubjectGroupId = " + csg.getSubjectGroupId() 
			+ ", SubjectGroupName = '" + csg.getSubjectGroupName() 
			+ "' where Id=" + csg.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from class_subject_group where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

}
