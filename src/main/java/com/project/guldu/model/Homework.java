package com.project.guldu.model;

public class Homework {
	private long id;
	private long sectionId;
	private long teacherId;
	private long subjectId;
	private String homeworkMessage;
	private int period;
	private String homeworkDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSectionId() {
		return sectionId;
	}
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}
	public long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public String getHomeworkMessage() {
		return homeworkMessage;
	}
	public void setHomeworkMessage(String homeworkMessage) {
		this.homeworkMessage = homeworkMessage;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getHomeworkDate() {
		return homeworkDate;
	}
	public void setHomeworkDate(String homeworkDate) {
		this.homeworkDate = homeworkDate;
	}
	
}
