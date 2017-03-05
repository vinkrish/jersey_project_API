package com.aanglearning.service.parent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Homework;
import com.aanglearning.service.JDBC;

public class AppHomeworkService {

	Statement stmt = null;

	public AppHomeworkService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Homework> getHomeworks(long sectionId, String lastDate) {
		String query = "select * from homework where SectionId = " + sectionId + " and HomeworkDate > '" + lastDate + "'";
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
	
	public List<Homework> getTodaysHomeworks(long sectionId, String currentDate) {
		String query = "select * from homework where SectionId = " + sectionId + " and HomeworkDate = '" + currentDate + "'";
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

}