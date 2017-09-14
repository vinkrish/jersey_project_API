package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.ClassEvent;
import com.aanglearning.service.DatabaseUtil;

public class ClassEventService {
	Statement stmt;

	public ClassEventService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ClassEvent> getEventClasses(long eventId) {
		String query = "select * from class_event where EventId = " + eventId;
		List<ClassEvent> eventClasses = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				ClassEvent ce = new ClassEvent();
				ce.setId(rs.getInt("Id"));
				ce.setEventId(rs.getInt("EventId"));
				ce.setClassId(rs.getLong("ClassId"));
				ce.setClassName(rs.getString("ClassName"));
				eventClasses.add(ce);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eventClasses;
	}
	
	public ClassEvent add(ClassEvent ce) {
		try {
			String query = "insert into class_event(Id, EventId, ClassId, ClassName) "
					+ "values ("
					+ ce.getId() + "," 
					+ ce.getEventId() + ","
					+ ce.getClassId() + ",'" 
					+ ce.getClassName() + "')";
			int pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ce.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ce;
	}
	
	public void delete(long id){
		try {
			String query = "delete from class_event where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
