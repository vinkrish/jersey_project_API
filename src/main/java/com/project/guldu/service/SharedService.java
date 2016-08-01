package com.project.guldu.service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.project.guldu.model.ClassSubjectGroup;
import com.project.guldu.model.Section;
import com.project.guldu.model.SubjectGroupSubject;

public class SharedService {

	Statement stmt = null;
	SectionService sectionService = null;
	ClassSubjectGroupService csgService = null;
	SubjectGroupSubjectService sgsService = null;

	public SharedService() {
		sectionService = new SectionService();
		csgService = new ClassSubjectGroupService();
		sgsService = new SubjectGroupSubjectService();
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void insertSubjectTeacher(long classId) {
		List<Section> sections = sectionService.getSectionList(classId);
		for (Section section: sections) {
			List<ClassSubjectGroup> csgList = csgService.getClassSubjectGroups(classId);
			for (ClassSubjectGroup csg: csgList) {
				List<SubjectGroupSubject> sgsList = sgsService.getSubjectGroupSubjects(csg.getSubjectGroupId());
				for (SubjectGroupSubject sgs: sgsList) {
					try {
						String query = "insert into subject_teacher(SectionId, SubjectId, TeacherId) "
								+ "values (" 
								+ section.getId() + ","
								+ sgs.getSubjectId() + ","
								+ 0 + ")";
						stmt.executeUpdate(query);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
