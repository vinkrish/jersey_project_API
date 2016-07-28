package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.Timetable;

public class TimetableService {
	Statement stmt = null;

	public TimetableService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Timetable> getTimetable(long sectionId) {
		String query = "select * from timetable where SectionId = " + sectionId;
		List<Timetable> timetableList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Timetable timetable = new Timetable();
				timetable.setId(rs.getLong("Id"));
				timetable.setSectionId(rs.getLong("SectionId"));
				timetable.setDayOfWeek(rs.getString("DayOfWeek"));
				timetable.setPeriodNo(rs.getInt("PeriodNo"));
				timetable.setSubjectId(rs.getLong("SubjectId"));
				timetableList.add(timetable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timetableList;
	}
	
	public void addTimetable(String timetableStr) {
		JSONArray timetableArray = new JSONArray(timetableStr);
		for (int i = 0; i < timetableArray.length(); i++) {
			JSONObject timetableJson = timetableArray.getJSONObject(i);
			Gson gson = new Gson();
			Timetable timetable = gson.fromJson(timetableJson.toString(), Timetable.class);
			try {
				String query = "insert into timetable(TimetableId, SectionId, DayOfWeek, PeriodNo, SubjectsId) "
						+ "values ("
						+ timetable.getId() + "," 
						+ timetable.getSectionId() + ","
						+ timetable.getDayOfWeek() +","
						+ timetable.getPeriodNo() + ",'"
						+ timetable.getSubjectId() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
