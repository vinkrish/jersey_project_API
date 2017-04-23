package com.aanglearning.model.app;

import java.util.List;

import com.aanglearning.model.entity.Attendance;
import com.aanglearning.model.entity.Student;

public class AttendanceSet {
	private List<Attendance> attendanceList;
	private List<Student> students;
	
	public List<Attendance> getAttendanceList() {
		return attendanceList;
	}
	public void setAttendanceList(List<Attendance> attendanceList) {
		this.attendanceList = attendanceList;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
}
