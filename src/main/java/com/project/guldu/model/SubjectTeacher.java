package com.project.guldu.model;

public class SubjectTeacher {
	private long id;
	private long sectionId;
	private long subjectId;
	private long teacherId;

	public long getId() {
		return id;
	}

	public void setId(long subjectTeacherId) {
		this.id = subjectTeacherId;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}
}
