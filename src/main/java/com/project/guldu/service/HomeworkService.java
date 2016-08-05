package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.Homework;

public class HomeworkService {
	Statement stmt = null;

	public HomeworkService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Homework> getHomeworkToday(long schoolId, String homeworkDate) {
		String query = "select * from homework where SchoolId = " + schoolId + " and HomeworkDate = " + homeworkDate;
		return getHomeworkList(query);
	}

	public List<Homework> getHomewokRange(long homeworkIndex) {
		String query = "select * from homework LIMIT 100 OFFSET " + homeworkIndex;
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
				homework.setTeacherId(rs.getLong("TeacherId"));
				homework.setSubjectId(rs.getLong("SubjectId"));
				homework.setHomeworkDate(rs.getString("HomeworkMessage"));
				homework.setPeriod(rs.getInt("Period"));
				homework.setHomeworkDate(rs.getString("HomeworkDate"));
				hwList.add(homework);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hwList;
	}

	public void addHomework(String homeworkStr) {
		System.out.println(homeworkStr);
		JSONArray homeworkArray = new JSONArray(homeworkStr);

		for (int i = 0; i < homeworkArray.length(); i++) {
			JSONObject homeworkJson = homeworkArray.getJSONObject(i);
			Gson gson = new Gson();
			Homework homework = gson.fromJson(homeworkJson.toString(), Homework.class);
			try {
				String query = "insert into homework(HomeworkId, SectionId, TeacherId, SubjectId, HomeworkMessage, Period, HomeworkDate) "
						+ "values (" 
						+ homework.getId() + "," 
						+ homework.getSectionId() + "," 
						+ homework.getTeacherId() + ",'"
						+ homework.getSubjectId() + "','" 
						+ homework.getHomeworkMessage() + "',"
						+ homework.getPeriod() + ",'" 
						+ homework.getHomeworkDate() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
