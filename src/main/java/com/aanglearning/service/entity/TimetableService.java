package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Section;
import com.aanglearning.model.entity.Timetable;
import com.aanglearning.service.DatabaseUtil;

public class TimetableService {
	Statement stmt;
	SectionService sectionService;

	public TimetableService() {
		try {
			sectionService = new SectionService();
			stmt = DatabaseUtil.getConnection().createStatement();
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
					+ timetable.getTimingFrom() + "','"
					+ timetable.getTimingTo() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			timetable.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timetable;
	}
	
	public void addWeekDayTimetable(long classId, long sectionId) {
		List<Timetable> timetables = getTimetableList("select * from timetable where SectionId = " + sectionId + " and DayOfWeek = 'Monday'");
		String[] weekDays = {"Tuesday", "Wednesday", "Thursday", "Friday"};
		for(Timetable timetable : timetables) {
			for(String day: weekDays) {
				String query = "insert into timetable(SectionId, DayOfWeek, PeriodNo, SubjectId, TimingFrom, TimingTo) "
						+ "values ("
						+ sectionId + ",'"
						+ day +"',"
						+ timetable.getPeriodNo() + ","
						+ timetable.getSubjectId() + ",'"
						+ timetable.getTimingFrom() + "','"
						+ timetable.getTimingTo() + "')";
				try {
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void copySectionTimetable(long classId, long sectionId) {
		List<Section> sections = sectionService.getSectionList(classId);
		List<Timetable> timetables = getTimetableList("select * from timetable where SectionId = " + sectionId + " and DayOfWeek = 'Monday'");
		String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		for (Section section : sections) {
			if(section.getId() != sectionId) {
				deleteForSection(section.getId());
				for(Timetable timetable : timetables) {
					for(String day: weekDays) {
						String query = "insert into timetable(SectionId, DayOfWeek, PeriodNo, SubjectId, TimingFrom, TimingTo) "
								+ "values ("
								+ section.getId() + ",'"
								+ day +"',"
								+ timetable.getPeriodNo() + ","
								+ timetable.getSubjectId() + ",'"
								+ timetable.getTimingFrom() + "','"
								+ timetable.getTimingTo() + "')";
						try {
							stmt.executeUpdate(query);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void addSaturdayTimetable(long classId, long sectionId) {
		List<Section> sections = sectionService.getSectionList(classId);
		List<Timetable> timetables = getTimetableList("select * from timetable where SectionId = " + sectionId + " and DayOfWeek = 'Monday'");
		for (Section section : sections) {
			for(Timetable timetable : timetables) {
				String query = "insert into timetable(SectionId, DayOfWeek, PeriodNo, SubjectId, TimingFrom, TimingTo) "
						+ "values ("
						+ section.getId() + ",'Saturday',"
						+ timetable.getPeriodNo() + ","
						+ timetable.getSubjectId() + ",'"
						+ timetable.getTimingFrom() + "','"
						+ timetable.getTimingTo() + "')";
				try {
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void update(Timetable timetable) {
		try {
			String query = "update timetable set SubjectId = " + timetable.getSubjectId()
			+ ", TimingFrom = '" + timetable.getTimingFrom()
			+ "', TimingTo = '" + timetable.getTimingTo()
			+ "' where Id=" + timetable.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long timetableId){
		try {
			String query = "delete from timetable where Id=" + timetableId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteForSection(long sectionId){
		try {
			String query = "delete from timetable where SectionId=" + sectionId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
