package com.aanglearning.service.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.app.TeacherTimetable;
import com.aanglearning.model.entity.Timetable;
import com.aanglearning.service.DatabaseUtil;

public class AppTimetableService {
	Statement stmt;

	public AppTimetableService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Timetable> getSectionTimetable(long sectionId) {
		String query = "select t.*, s.SubjectName, st.TeacherName from timetable t, subject s, subject_teacher st "
				+ "where t.SectionId = " + sectionId + " and s.Id = t.SubjectId and st.SectionId = t.SectionId and st.SubjectId = t.SubjectId group by Id";
		return getTimetableList(query);
	}

	public List<TeacherTimetable> getTeacherTimetable(long teacherId) {
		/*String query = "select t.*, s.SubjectName, s.TeacherName from timetable t, subject_teacher s "
				+ "where s.TeacherId = " + teacherId + " and t.SectionId = s.SectionId and t.SubjectId = s.SubjectId";*/
		String sql = "select t.*, c.ClassName, s.SectionName, st.SubjectName, st.TeacherName "
				+ "from timetable t, class c, section s, subject_teacher st "
				+ "where s.Id = t.SectionId and st.TeacherId = " + teacherId + " and t.SectionId = st.SectionId and t.SubjectId = st.SubjectId "
				+ "and c.Id = (select ClassId from section where Id = t.SectionId)";
		List<TeacherTimetable> timetableList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TeacherTimetable timetable = new TeacherTimetable();
				timetable.setId(rs.getLong("Id"));
				timetable.setSectionId(rs.getLong("SectionId"));
				timetable.setClassName(rs.getString("ClassName"));
				timetable.setSectionName(rs.getString("SectionName"));
				timetable.setDayOfWeek(rs.getString("DayOfWeek"));
				timetable.setPeriodNo(rs.getInt("PeriodNo"));
				timetable.setSubjectId(rs.getLong("SubjectId"));
				timetable.setSubjectName(rs.getString("SubjectName"));
				timetable.setTeacherName(rs.getString("TeacherName"));
				timetable.setTimingFrom(rs.getString("TimingFrom"));
				timetable.setTimingTo(rs.getString("TimingTo"));
				timetableList.add(timetable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timetableList;
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
				timetable.setSubjectName(rs.getString("SubjectName"));
				timetable.setTeacherName(rs.getString("TeacherName"));
				timetable.setTimingFrom(rs.getString("TimingFrom"));
				timetable.setTimingTo(rs.getString("TimingTo"));
				timetableList.add(timetable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timetableList;
	}

}
