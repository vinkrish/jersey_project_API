package com.aanglearning.model.app;

import java.util.List;

import com.aanglearning.model.entity.Student;

public class SmsStudent {
	private List<Student> students;
	private Sms sms;
	
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public Sms getSms() {
		return sms;
	}
	public void setSms(Sms sms) {
		this.sms = sms;
	}
	
}
