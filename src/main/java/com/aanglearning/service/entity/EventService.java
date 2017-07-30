package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Event;
import com.aanglearning.service.DatabaseUtil;

public class EventService {
	Statement stmt;

	public EventService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Event add(Event event) {
		try {
			String query = "insert into event(Id, SchoolId, EventTitle, EventDescription, StartDate, EndDate, StartTime, EndTime, NoOfDays, "
					+ "IsContinuousDays, IsFullDayEvent, IsRecurring, CreatedBy, CreatedDate, ParentEventId) "
					+ "values ("
					+ event.getId() + "," 
					+ event.getSchoolId() + ",'"
					+ event.getEventTitle() + "','"
					+ event.getEventDescription() + "','"
					+ event.getStartDate() + "','"
					+ event.getEndDate() + "',"
					+ event.getStartTime() + ","
					+ event.getEndTime() + ","
					+ event.getNoOfDays() + ","
					+ event.isContinuousDays() + ","
					+ event.isFullDayEvent() + ","
					+ event.isRecurring() + ",'"
					+ event.getCreatedBy() + "','"
					+ event.getCreatedDate() + "',"
					+ event.getParentEventId() + ")";
			int pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()){
			    pk = rs.getInt(1);
			}
			event.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return event;
	}
	
	public List<Event> getSchoolEvents(long schoolId) {
		String query = "select * from event where SchoolId = " + schoolId;
		return getEvents(query);
	}
	
	private List<Event> getEvents(String query) {
		List<Event> events = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
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
				event.setContinuousDays(rs.getBoolean("IsContinuousDays"));
				event.setFullDayEvent(rs.getBoolean("IsFullDayEvent"));
				event.setRecurring(rs.getBoolean("IsRecurring"));
				event.setCreatedBy(rs.getString("CreatedBy"));
				event.setCreatedDate(rs.getString("CreatedDate"));
				event.setParentEventId(rs.getInt("ParentEventId"));
				events.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	public void update(Event event) {
		try {
			String query = "update event set"
					+ " EventTitle = '" + event.getEventTitle()
					+ "', EventDescription = '" + event.getEventDescription()
					+ "', StartDate = '" + event.getStartDate()
					+ "', EndDate = '" + event.getEndDate()
					+ ", StartTime = " + event.getStartTime()
					+ ", EndTime = " + event.getEndTime()
					+ ", NoOfDays = " + event.getNoOfDays()
					+ ", IsContinuousDays = " + event.isContinuousDays()
					+ ", IsFullDayEvent = " + event.isFullDayEvent()
					+ ", IsRecurring = " + event.isRecurring()
					+ ", CreatedBy = '" + event.getCreatedBy()
					+ "', CreatedDate = '" + event.getCreatedDate()
					+ "', ParentEventId = " + event.getParentEventId()
					+ " where Id = " + event.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id){
		try {
			String query = "delete from event where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
