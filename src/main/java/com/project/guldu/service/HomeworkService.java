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
import com.project.guldu.model.SubjectTeacher;

public class HomeworkService {
	Statement stmt = null;
	SubjectTeacherService sts;

	public HomeworkService() {
		try {
			stmt = JDBC.getConnection().createStatement();
			sts = new SubjectTeacherService();
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
		List<Homework> hwListExist = getHomeworkToday(sectionId, homeworkDate);
		List<SubjectTeacher> stList = sts.getSubjectTeacher(sectionId);
		for(SubjectTeacher st: stList) {
			boolean hwExist = false;
			for(Homework homework: hwListExist){
				if(homework.getSubjectId() == st.getSubjectId()){
					hwExist = true;
					break;
				}
			}
			if(!hwExist){
				Homework h = new Homework();
				h.setId(0);
				h.setSectionId(sectionId);
				h.setSubjectId(st.getSubjectId());
				h.setSubjectName(st.getSubjectName());
				h.setHomeworkDate(homeworkDate);
				h.setHomeworkMessage("");
				hwListExist.add(h);
			}
		}
		return hwListExist;
	}

	public void addHomework(String homeworkStr) {
		System.out.println(homeworkStr);
		JSONArray homeworkArray = new JSONArray(homeworkStr);

		for (int i = 0; i < homeworkArray.length(); i++) {
			JSONObject homeworkJson = homeworkArray.getJSONObject(i);
			Gson gson = new Gson();
			Homework homework = gson.fromJson(homeworkJson.toString(), Homework.class);
			try {
				String query = "insert into homework(Id, SectionId, SubjectId, SubjectName, HomeworkMessage, HomeworkDate) "
						+ "values (" 
						+ homework.getId() + "," 
						+ homework.getSectionId() + "," 
						+ homework.getSubjectId() + ",'" 
						+ homework.getSubjectName() + "','"
						+ homework.getHomeworkMessage() + "','"
						+ homework.getHomeworkDate() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long homeworkId){
		try {
			String query = "delete from homework where Id=" + homeworkId;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
