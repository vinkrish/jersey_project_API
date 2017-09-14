package com.aanglearning.service.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aanglearning.model.entity.Event;
import com.aanglearning.service.DatabaseUtil;

public class EventService {
	Connection connection;
	
	public EventService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Event add(Event event) {
		String query = "insert into event(Id, SchoolId, EventTitle, EventDescription, StartDate, EndDate, StartTime, EndTime, NoOfDays, "
				+ "IsContinuousDays, IsFullDayEvent, IsRecurring, CreatedBy, CreatedDate, ParentEventId, IsSchool) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
		    PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setLong(1, event.getId());
	    	preparedStatement.setLong(2, event.getSchoolId());
	    	preparedStatement.setString(3, event.getEventTitle());
	    	preparedStatement.setString(4, event.getEventDescription());
	    	preparedStatement.setString(5, event.getStartDate());
	    	preparedStatement.setString(6, event.getEndDate());
	    	preparedStatement.setLong(7, event.getStartTime());
	    	preparedStatement.setLong(8, event.getEndTime());
	    	preparedStatement.setInt(9, event.getNoOfDays());
	    	preparedStatement.setBoolean(10, event.getIsContinuousDays());
	    	preparedStatement.setBoolean(11, event.getIsFullDayEvent());
	    	preparedStatement.setBoolean(12, event.getIsRecurring());
	    	preparedStatement.setString(13, event.getCreatedBy());
	    	preparedStatement.setString(14, event.getCreatedDate());
	    	preparedStatement.setInt(15, event.getParentEventId());
	    	preparedStatement.setBoolean(16, event.getIsSchool());
	    	preparedStatement.executeUpdate();
		    
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    int pk = 0;
			if (rs.next()){
			    pk = rs.getInt(1);
			}
			event.setId(pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}

	public List<Event> getSchoolEvents(long schoolId) {
		String query = "select * from event where SchoolId = " + schoolId;
		return getEvents(query);
	}
	
	public List<Event> getStudentEvents(long schoolId, long classId) {
		String query1 = "select * from event where SchoolId = " + schoolId + " and IsSchool = 1";
		String query2 = "select * from event where Id in (select EventId from class_event where ClassId = " + classId + ")";
		Set<Event> set = new HashSet<Event>(getEvents(query1));
		set.addAll(getEvents(query2));
		return new ArrayList<Event>(set);
	}
	
	public List<Event> getTeacherEvents(long schoolId, long teacherId) {
		String query1 = "select * from event where SchoolId = " + schoolId + " and IsSchool = 1";
		String query2 = "select * from event where Id in (select EventId from class_event where ClassId in "
				+ "(select * from class where Id in (select ClassId from section where Id in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId))))";
		Set<Event> set = new HashSet<Event>(getEvents(query1));
		set.addAll(getEvents(query2));
		return new ArrayList<Event>(set);
	}

	private List<Event> getEvents(String query) {
		List<Event> events = new ArrayList<>();
		try {
			ResultSet rs = connection.createStatement().executeQuery(query);
			while (rs.next()) {
				Event event = new Event();
				event.setId(rs.getInt("Id"));
				event.setSchoolId(rs.getLong("SchoolId"));
				event.setEventTitle(rs.getString("EventTitle"));
				event.setEventDescription(rs.getString("EventDescription"));
				event.setStartDate(rs.getString("StartDate"));
				event.setEndDate(rs.getString("EndDate"));
				event.setStartTime(rs.getLong("StartTime"));
				event.setEndTime(rs.getLong("EndTime"));
				event.setNoOfDays(rs.getInt("NoOfDays"));
				event.setIsContinuousDays(rs.getBoolean("IsContinuousDays"));
				event.setIsFullDayEvent(rs.getBoolean("IsFullDayEvent"));
				event.setIsRecurring(rs.getBoolean("IsRecurring"));
				event.setCreatedBy(rs.getString("CreatedBy"));
				event.setCreatedDate(rs.getString("CreatedDate"));
				event.setParentEventId(rs.getInt("ParentEventId"));
				event.setIsSchool(rs.getBoolean("IsSchool"));
				events.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}

	public void update(Event event) {
		try {
			String query = "update event set EventTitle = ?, EventDescription = ?, StartDate = ?, EndDate = ?, StartTime = ?, EndTime = ?, "
					+ "NoOfDays=?, IsContinuousDays=?, IsFullDayEvent=?, IsRecurring=?, CreatedBy=?, CreatedDate=?, ParentEventId=?, IsSchool=? "
					+ "where Id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, event.getEventTitle());
			preparedStatement.setString(2, event.getEventDescription());
			preparedStatement.setString(3, event.getStartDate());
			preparedStatement.setString(4, event.getEndDate());
			preparedStatement.setLong(5, event.getStartTime());
			preparedStatement.setLong(6, event.getEndTime());
			preparedStatement.setInt(7, event.getNoOfDays());
			preparedStatement.setBoolean(8, event.getIsContinuousDays());
	    	preparedStatement.setBoolean(9, event.getIsFullDayEvent());
	    	preparedStatement.setBoolean(10, event.getIsRecurring());
	    	preparedStatement.setString(11, event.getCreatedBy());
	    	preparedStatement.setString(12, event.getCreatedDate());
	    	preparedStatement.setInt(13, event.getParentEventId());
	    	preparedStatement.setBoolean(14, event.getIsSchool());
	    	preparedStatement.setInt(15, event.getId());
			preparedStatement .executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(int id) {
		String query = "delete from event where Id=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
