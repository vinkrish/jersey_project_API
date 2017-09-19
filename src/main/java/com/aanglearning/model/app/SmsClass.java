package com.aanglearning.model.app;

import java.util.List;

import com.aanglearning.model.entity.Clas;

public class SmsClass {
	private List<Clas> classes;
	private Sms sms;
	
	public List<Clas> getClasses() {
		return classes;
	}
	public void setClasses(List<Clas> classes) {
		this.classes = classes;
	}
	public Sms getSms() {
		return sms;
	}
	public void setSms(Sms sms) {
		this.sms = sms;
	}
	
}
