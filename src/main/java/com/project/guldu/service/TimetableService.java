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
		return getTimetableList(query);
	}
	
	public List<Timetable> getTimetableForDay(long sectionId, String day) {
		String query = "select * from timetable where SectionId = " + sectionId + " and DayOfWeek = '" + day + "'";
		return getTimetableList(query);
	}
	
	public List<Timetable> getTimetableList(String query) {
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
				//timetable.setSubjectName(rs.getString("SubjectName"));
				timetable.setTimingFrom(rs.getString("TimingFrom"));
				timetable.setTimingTo(rs.getString("TimingTo"));
				timetableList.add(timetable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timetableList;
	}
	
	public Timetable add(Timetable timetable) {
		try {
			String query = "insert into timetable(Id, SectionId, DayOfWeek, PeriodNo, SubjectId, TimingFrom, TimingTo) "
					+ "values ("
					+ timetable.getId() + "," 
					+ timetable.getSectionId() + ",'"
					+ timetable.getDayOfWeek() +"',"
					+ timetable.getPeriodNo() + ","
					+ timetable.getSubjectId() + ",'"
					//+ timetable.getSubjectName() + "','"
					+ timetable.getTimingFrom() + "','"
					+ timetable.getTimingTo() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			timetable.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timetable;
	}
	
	public void update(Timetable timetable) {
		try {
			String query = "update timetable set SubjectId = " + timetable.getSubjectId()
			//+ ", SubjectName = '" + timetable.getSubjectName() 
			+ ", TimingFrom = '" + timetable.getTimingFrom()
			+ "', TimingTo = '" + timetable.getTimingTo()
			+ "' where Id=" + timetable.getId();
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long timetableId){
		try {
			String query = "delete from timetable where Id=" + timetableId;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addTimetable(String timetableStr) {
		JSONArray timetableArray = new JSONArray(timetableStr);
		for (int i = 0; i < timetableArray.length(); i++) {
			JSONObject timetableJson = timetableArray.getJSONObject(i);
			Gson gson = new Gson();
			Timetable timetable = gson.fromJson(timetableJson.toString(), Timetable.class);
			try {
				String query = "insert into timetable(Id, SectionId, DayOfWeek, PeriodNo, SubjectId) "
						+ "values ("
						+ timetable.getId() + "," 
						+ timetable.getSectionId() + ",'"
						+ timetable.getDayOfWeek() +"',"
						+ timetable.getPeriodNo() + ","
						+ timetable.getSubjectId() + ")";
						//+ timetable.getSubjectName() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
