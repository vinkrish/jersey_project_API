package com.project.guldu.model;

public class CceCoschAspectGrade {
	private long id;
	private long sectionId;
	private long studentId;
	private long aspectId;
	private int type;
	private int term;
	private String grade;
	private int vale;
	private String description;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSectionId() {
		return sectionId;
	}
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public long getAspectId() {
		return aspectId;
	}
	public void setAspectId(long aspectId) {
		this.aspectId = aspectId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getVale() {
		return vale;
	}
	public void setVale(int vale) {
		this.vale = vale;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
