package com.aanglearning.model.entity;

public class Service {
	private long id;
	private long schoolId;
	private boolean isMessage;
	private boolean isSms;
	private boolean isChat;
	private boolean isAttendance;
	private boolean isHomework;
	private boolean isAttendanceSms;
	private boolean isHomeworkSms;
	private boolean isTimetable;
	private boolean isReport;
	private boolean isSpeak;
	private boolean isGallery;
	
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
	public boolean getIsChat() {
		return isChat;
	}
	public void setIsChat(boolean isChat) {
		this.isChat = isChat;
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
	public boolean getIsTimetable() {
		return isTimetable;
	}
	public void setIsTimetable(boolean isTimetable) {
		this.isTimetable = isTimetable;
	}
	public boolean getIsReport() {
		return isReport;
	}
	public void setIsReport(boolean isReport) {
		this.isReport = isReport;
	}
	public boolean getIsSpeak() {
		return isSpeak;
	}
	public void setIsSpeak(boolean isSpeak) {
		this.isSpeak = isSpeak;
	}
	public boolean getIsGallery() {
		return isGallery;
	}
	public void setIsGallery(boolean isGallery) {
		this.isGallery = isGallery;
	}
}
