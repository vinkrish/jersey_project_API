package com.aanglearning.model.entity;

public class Service {
	private long id;
	private long schoolId;
	private boolean isMessage;
	private boolean isSms;
	private boolean isAttendance;
	private boolean isHomework;
	private boolean isAttendanceSms;
	private boolean isHomeworkSms;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}
	public boolean getIsMessage() {
		return isMessage;
	}
	public void setIsMessage(boolean isMessage) {
		this.isMessage = isMessage;
	}
	public boolean getIsSms() {
		return isSms;
	}
	public void setIsSms(boolean isSms) {
		this.isSms = isSms;
	}
	public boolean getIsAttendance() {
		return isAttendance;
	}
	public void setIsAttendance(boolean isAttendance) {
		this.isAttendance = isAttendance;
	}
	public boolean getIsHomework() {
		return isHomework;
	}
	public void setIsHomework(boolean isHomework) {
		this.isHomework = isHomework;
	}
	public boolean getIsAttendanceSms() {
		return isAttendanceSms;
	}
	public void setIsAttendanceSms(boolean isAttendanceSms) {
		this.isAttendanceSms = isAttendanceSms;
	}
	public boolean getIsHomeworkSms() {
		return isHomeworkSms;
	}
	public void setIsHomeworkSms(boolean isHomeworkSms) {
		this.isHomeworkSms = isHomeworkSms;
	}
}
