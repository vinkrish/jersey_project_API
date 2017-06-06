package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Homework;
import com.aanglearning.model.entity.SubjectTeacher;
import com.aanglearning.resource.entity.SubjectTeacherResource;
import com.aanglearning.service.DatabaseUtil;

public class HomeworkService {
	Statement stmt;
	SubjectTeacherResource str = new SubjectTeacherResource();

	public HomeworkService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Homework> getHomeworkToday(long sectionId, String homeworkDate) {
		String query = "select * from homework where SectionId = " + sectionId + " and HomeworkDate = '" + homeworkDate + "'";
		return getHomeworkList(query);
	}
	
	public List<Homework> getHomeworkList(String query) {
		List<Homework> hwList = new ArrayList<Homework>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Homework homework = new Homework();
				homework.setId(rs.getLong("Id"));
				homework.setSectionId(rs.getLong("SectionId"));
				homework.setSubjectId(rs.getLong("SubjectId"));
				homework.setSubjectName(rs.getString("SubjectName"));
				homework.setHomeworkMessage(rs.getString("HomeworkMessage"));
				homework.setHomeworkDate(rs.getString("HomeworkDate"));
				hwList.add(homework);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hwList;
	}
	
	public List<Homework> getUnHomeworkToday(long sectionId, String homeworkDate){
		String query = "select * from subject_teacher "
				+ "where SubjectId not in "
				+ "(select SubjectId from homework where SectionId = " + sectionId + " and HomeworkDate = '" + homeworkDate + "')"
				+ "and SectionId = " + sectionId;
		
		List<Homework> homeworkList = getHomeworkToday(sectionId, homeworkDate);
		List<SubjectTeacher> stList = str.getSubjectTeacherByQuery(query);
		for(SubjectTeacher st: stList) {
			Homework h = new Homework();
			h.setId(0);
			h.setSectionId(sectionId);
			h.setSubjectId(st.getSubjectId());
			h.setSubjectName(st.getSubjectName());
			h.setHomeworkDate(homeworkDate);
			h.setHomeworkMessage("");
			homeworkList.add(h);
		}
		return homeworkList;
	}
	
	public Homework add(Homework homework) {
		try {
			String query = "insert into homework(Id, SectionId, SubjectId, SubjectName, HomeworkMessage, HomeworkDate) "
					+ "values (" 
					+ homework.getId() + "," 
					+ homework.getSectionId() + "," 
					+ homework.getSubjectId() + ",'" 
					+ homework.getSubjectName() + "','"
					+ homework.getHomeworkMessage() + "','"
					+ homework.getHomeworkDate() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			homework.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return homework;
	}
	
	public void update(Homework homework) {
		try {
			String query = "update homework set HomeworkMessage='"+ homework.getHomeworkMessage() 
			+ "' where Id=" + homework.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long homeworkId){
		try {
			String query = "delete from homework where Id=" + homeworkId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteList(List<Homework> homeworks) {
		try {
			for(Homework homework: homeworks) {
				String query = "delete from homework where Id=" + homework.getId();
				stmt.executeUpdate(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
