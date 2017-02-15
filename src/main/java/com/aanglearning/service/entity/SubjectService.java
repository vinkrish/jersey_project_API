package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.ClassSubjectGroup;
import com.aanglearning.model.entity.Subject;
import com.aanglearning.model.entity.SubjectGroupSubject;
import com.aanglearning.service.JDBC;

public class SubjectService {
	Statement stmt = null;
	ClassSubjectGroupService csgService;
	SubjectGroupSubjectService sgsService;

	public SubjectService() {
		try {
			stmt = JDBC.getConnection().createStatement();
			csgService = new ClassSubjectGroupService();
			sgsService = new SubjectGroupSubjectService();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Subject> getSubjectList(long schoolId) {
		String query ="select * from subject where SchoolId = " + schoolId;
		List<Subject> subjects = new ArrayList<>();
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
				subjects.add(subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjects;
	}
	
	public Subject getSubject(String query) {
		Subject subject = new Subject();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				subject.setId(rs.getLong("Id"));
				subject.setSchoolId(rs.getLong("SchoolId"));
				subject.setSubjectName(rs.getString("SubjectName"));
				subject.setPartitionType(rs.getInt("partitionType"));
				subject.setTheorySubjectId(rs.getLong("TheorySubjectId"));
				subject.setPracticalSubjectId(rs.getLong("PracticalSubjectId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subject;
	}
	
	public List<Subject> getPartitionSubjects(long subjectId) {
		String query ="select * from subject where Id = " + subjectId;
		List<Subject> subjects = new ArrayList<>();
		Subject parentSubject = getSubject(query);
		if(parentSubject.getPartitionType() == 1) {
			String theorySubject ="select * from subject where Id = " + parentSubject.getTheorySubjectId();
			subjects.add(getSubject(theorySubject));
			String practicalSubject ="select * from subject where Id = " + parentSubject.getPracticalSubjectId();
			subjects.add(getSubject(practicalSubject));
		}
		return subjects;
	}
	
	public List<Subject> getClassSubjects(long classId) {
		List<Subject> subjectList = new ArrayList<>();
		List<ClassSubjectGroup> csgList = csgService.getClassSubjectGroups(classId);
		for(ClassSubjectGroup csg: csgList) {
			List<SubjectGroupSubject> sgsList = sgsService.getSubjectGroupSubjects(csg.getSubjectGroupId());
			for(SubjectGroupSubject sgs: sgsList) {
				Subject subject = new Subject();
				subject.setId(sgs.getSubjectId());
				subject.setSubjectName(sgs.getSubjectName());
				subjectList.add(subject);
			}
		}
		return subjectList;
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
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long subjectId) {
		try {
			String query = "delete from subject where Id=" + subjectId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
