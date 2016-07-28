package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.Clas;

public class ClassService {
	Statement stmt = null;

	public ClassService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Clas> getClassList(long schoolId) {
		String query = "select * from class where SchoolId = " + schoolId;
		List<Clas> classList = new ArrayList<Clas>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Clas clas = new Clas();
				clas.setId(rs.getLong("Id"));
				clas.setClassName(rs.getString("ClassName"));
				clas.setSchoolId(rs.getLong("SchoolId"));
				clas.setAttendanceType(rs.getString("AttendanceType"));
				clas.setHomeworkType(rs.getString("HomeworkType"));
				classList.add(clas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classList;
	}
	
	public void addClasses(String classStr) {
		JSONArray classArray = new JSONArray(classStr);
		for (int i = 0; i < classArray.length(); i++) {
			JSONObject classJson = classArray.getJSONObject(i);
			Gson gson = new Gson();
			Clas clas = gson.fromJson(classJson.toString(), Clas.class);
			try {
				String query = "insert into class(Id, ClassName, SchoolId, AttendanceType, HomeworkType) "
						+ "values ("
						+ clas.getId() + ",'" 
						+ clas.getClassName() + "',"
						+ clas.getSchoolId() + ",'" 
						+ clas.getAttendanceType() + "','" 
						+ clas.getHomeworkType() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Clas add(Clas clas) {
		try {
			String query = "insert into class(Id, ClassName, SchoolId, AttendanceType, HomeworkType) "
					+ "values ("
					+ clas.getId() + ",'" 
					+ clas.getClassName() + "',"
					+ clas.getSchoolId() + ",'" 
					+ clas.getAttendanceType() + "','" 
					+ clas.getHomeworkType() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			clas.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clas;
	}
	
	public void update(Clas clas) {
		try {
			String query = "update class set ClassName='"+clas.getClassName() + "', AttendanceType='" + clas.getAttendanceType() 
			+ "', HomeworkType='" + clas.getHomeworkType() + "' where Id=" + clas.getId();
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long classId){
		try {
			String query = "delete from class where Id=" + classId;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
