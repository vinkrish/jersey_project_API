package com.project.guldu.model;

public class SubActivity {
	private long id;
	private long activityId;
	private String subActivityName;
	private float maximumMark;
	private float weightage;
	private int calculation;
	private float subActivityAvg;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public String getSubActivityName() {
		return subActivityName;
	}
	public void setSubActivityName(String subActivityName) {
		this.subActivityName = subActivityName;
	}
	public float getMaximumMark() {
		return maximumMark;
	}
	public void setMaximumMark(float maximumMark) {
		this.maximumMark = maximumMark;
	}
	public float getWeightage() {
		return weightage;
	}
	public void setWeightage(float weightage) {
		this.weightage = weightage;
	}
	public int getCalculation() {
		return calculation;
	}
	public void setCalculation(int calculation) {
		this.calculation = calculation;
	}
	public float getSubActivityAvg() {
		return subActivityAvg;
	}
	public void setSubActivityAvg(float subActivityAvg) {
		this.subActivityAvg = subActivityAvg;
	}
	
}