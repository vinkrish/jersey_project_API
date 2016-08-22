package com.project.guldu.model;

public class AuthResponse {
	private long schoolId;
	private String schoolName;
	private String token;
	
	public AuthResponse(){
		
	}
	
	public AuthResponse(long schoolId, String schoolName, String token) {
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.token = token;
	}
	
	public long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
