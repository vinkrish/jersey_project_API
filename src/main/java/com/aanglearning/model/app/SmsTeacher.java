package com.aanglearning.model.app;

import java.util.List;

import com.aanglearning.model.entity.Teacher;

public class SmsTeacher {
	private List<Teacher> teachres;
	private Sms sms;
	
	public List<Teacher> getTeachres() {
		return teachres;
	}
	public void setTeachres(List<Teacher> teachres) {
		this.teachres = teachres;
	}
	public Sms getSms() {
		return sms;
	}
	public void setSms(Sms sms) {
		this.sms = sms;
	}
	
}
