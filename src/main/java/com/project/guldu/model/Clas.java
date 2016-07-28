package com.project.guldu.model;

public class Clas {
	private long id;
	private String className;
	private long schoolId;
	private String attendanceType;
	private String homeworkType;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}
	public String getAttendanceType() {
		return attendanceType;
	}
	public void setAttendanceType(String attendanceType) {
		this.attendanceType = attendanceType;
	}
	public String getHomeworkType() {
		return homeworkType;
	}
	public void setHomeworkType(String homeworkType) {
		this.homeworkType = homeworkType;
	}
	
}
