package com.project.guldu.model;

public class ExamSubject {
	private long id;
	private long examId;
	private long subjectId;
	private float maximumMark;
	private float failMark;
	private float percentage;
	
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
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public float getMaximumMark() {
		return maximumMark;
	}
	public void setMaximumMark(float maximumMark) {
		this.maximumMark = maximumMark;
	}
	public float getFailMark() {
		return failMark;
	}
	public void setFailMark(float failMark) {
		this.failMark = failMark;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	
}
