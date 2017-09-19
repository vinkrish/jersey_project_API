package com.aanglearning.model.app;

import java.util.List;

import com.aanglearning.model.entity.Section;

public class SmsSection {
	private List<Section> sections;
	private Sms sms;
	
	public List<Section> getSections() {
		return sections;
	}
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	public Sms getSms() {
		return sms;
	}
	public void setSms(Sms sms) {
		this.sms = sms;
	}
	
}
