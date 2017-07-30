package com.aanglearning.model.entity;

public class Event {
	private int id;
	private long schoolId;
	private String eventTitle;
	private String eventDescription;
	private String startDate;
	private String endDate;
	private long startTime;
	private long endTime;
	private int noOfDays;
	private boolean continuousDays;
	private boolean fullDayEvent;
	private boolean recurring;
	private String createdBy;
	private String createdDate;
	private int parentEventId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public boolean isContinuousDays() {
		return continuousDays;
	}
	public void setContinuousDays(boolean continuousDays) {
		this.continuousDays = continuousDays;
	}
	public boolean isFullDayEvent() {
		return fullDayEvent;
	}
	public void setFullDayEvent(boolean fullDayEvent) {
		this.fullDayEvent = fullDayEvent;
	}
	public boolean isRecurring() {
		return recurring;
	}
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getParentEventId() {
		return parentEventId;
	}
	public void setParentEventId(int parentEventId) {
		this.parentEventId = parentEventId;
	}
	
}
