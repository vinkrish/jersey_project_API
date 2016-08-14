package com.project.guldu.model;

public class ExamSubjectGroup {
	private long id;
	private long examId;
	private long subjectGroupId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExamId() {
		return examId;
	}
	public void setExamId(long examId) {
		this.examId = examId;
	}
	public long getSubjectGroupId() {
		return subjectGroupId;
	}
	public void setSubjectGroupId(long subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

}
