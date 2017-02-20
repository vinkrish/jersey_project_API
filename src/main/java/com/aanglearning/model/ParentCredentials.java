package com.aanglearning.model;

import java.util.ArrayList;

public class ParentCredentials {
	private String token;
	private ArrayList<ChildInfo> info;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ArrayList<ChildInfo> getInfo() {
		return info;
	}
	public void setInfo(ArrayList<ChildInfo> info) {
		this.info = info;
	}
	
}
