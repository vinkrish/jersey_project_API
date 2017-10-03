package com.aanglearning.model;

public class Client {
	private long id;
	private String name;
	private String mobile;
	private String email;
	private String password;
	private int smsCredentials;
	private int smsCount;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSmsCredentials() {
		return smsCredentials;
	}
	public void setSmsCredentials(int smsCredentials) {
		this.smsCredentials = smsCredentials;
	}
	public int getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}

}
