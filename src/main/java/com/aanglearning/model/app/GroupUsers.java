package com.aanglearning.model.app;

import java.util.List;

import com.aanglearning.model.entity.Student;
import com.aanglearning.model.entity.Teacher;

public class GroupUsers {
	private List<UserGroup> userGroupList;
	private List<Student> students;
	private List<Teacher> teachers;

	public List<UserGroup> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<UserGroup> userGroupList) {
		this.userGroupList = userGroupList;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

}
