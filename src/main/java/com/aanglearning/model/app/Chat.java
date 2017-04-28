package com.aanglearning.model.app;

public class Chat {
	private long id;
	private long studentId;
	private long teacherId;
	private long createdBy;
	private String creatorRole;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatorRole() {
		return creatorRole;
	}
	public void setCreatorRole(String creatorRole) {
		this.creatorRole = creatorRole;
	}
	
}
