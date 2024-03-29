package com.aanglearning.model.app;

public class Groups {
	private long id;
	private String name;
	private boolean school;
	private long sectionId;
	private boolean section;
	private long classId;
	private boolean clas;
	private long createdBy;
	private String creatorName;
	private String creatorRole;
	private String createdDate;
	private boolean active;
	private long schoolId;

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
	
	public boolean isSchool() {
		return school;
	}

	public void setSchool(boolean school) {
		this.school = school;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public boolean isSection() {
		return section;
	}

	public void setSection(boolean section) {
		this.section = section;
	}

	public boolean isClas() {
		return clas;
	}

	public void setClas(boolean clas) {
		this.clas = clas;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getClassId() {
		return classId;
	}

	public void setClassId(long classId) {
		this.classId = classId;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreatorRole() {
		return creatorRole;
	}

	public void setCreatorRole(String creatorRole) {
		this.creatorRole = creatorRole;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

}
