package com.aanglearning.model.app;

import java.util.List;

import com.aanglearning.model.entity.Teacher;

public class SmsTeacher {
	private List<Teacher> teachers;
	private Sms sms;
	
	public List<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	public Sms getSms() {
		return sms;
	}
	public void setSms(Sms sms) {
		this.sms = sms;
	}
	
}
