package com.aanglearning.service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.aanglearning.model.entity.Clas;
import com.aanglearning.model.entity.ClassSubjectGroup;
import com.aanglearning.model.entity.Section;
import com.aanglearning.model.entity.SubjectGroupSubject;
import com.aanglearning.service.entity.ClassSubjectGroupService;
import com.aanglearning.service.entity.SectionService;
import com.aanglearning.service.entity.SubjectGroupSubjectService;

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

	
	public void insertSubjectTeacher(Clas clas) {
		List<Section> sections = sectionService.getSectionList(clas.getId());
		for (Section section: sections) {
			List<ClassSubjectGroup> csgList = csgService.getClassSubjectGroups(clas.getId());
			for (ClassSubjectGroup csg: csgList) {
				List<SubjectGroupSubject> sgsList = sgsService.getSubjectGroupSubjects(csg.getSubjectGroupId());
				for (SubjectGroupSubject sgs: sgsList) {
					try {
						String query = "insert into subject_teacher(SectionId, SubjectId, SubjectName, TeacherId) "
								+ "values (" 
								+ section.getId() + ","
								+ sgs.getSubjectId() + ",'"
								+ sgs.getSubjectName() + "',"
								+ 0 + ")";
						System.out.println(query);
						stmt.executeUpdate(query);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
