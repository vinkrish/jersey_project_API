package com.aanglearning.model;

import java.util.ArrayList;

public class ParentCredentials {
	private String authToken;
	private ArrayList<ChildInfo> info;
	
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public ArrayList<ChildInfo> getInfo() {
		return info;
	}
	public void setInfo(ArrayList<ChildInfo> info) {
		this.info = info;
	}
	
}
