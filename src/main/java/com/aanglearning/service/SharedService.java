package com.aanglearning.service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.aanglearning.model.entity.Clas;
import com.aanglearning.model.entity.Section;
import com.aanglearning.service.entity.ClassSubjectGroupService;
import com.aanglearning.service.entity.SectionService;
import com.aanglearning.service.entity.SubjectGroupSubjectService;

public class SharedService {
	Statement stmt;
	SectionService sectionService;
	ClassSubjectGroupService csgService;
	SubjectGroupSubjectService sgsService;

	public SharedService() {
		sectionService = new SectionService();
		csgService = new ClassSubjectGroupService();
		sgsService = new SubjectGroupSubjectService();
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertSubjectTeacher(Clas clas) {
		List<Section> sections = sectionService.getSectionList(clas.getId());

		/*
		 * for (Section section : sections) { List<ClassSubjectGroup> csgList =
		 * csgService.getClassSubjectGroups(clas.getId()); for
		 * (ClassSubjectGroup csg : csgList) { List<SubjectGroupSubject> sgsList
		 * = sgsService.getSubjectGroupSubjects(csg.getSubjectGroupId()); for
		 * (SubjectGroupSubject sgs : sgsList) { try { String query =
		 * "insert into subject_teacher(SectionId, SubjectId, SubjectName, TeacherId) "
		 * + "values (" + section.getId() + "," + sgs.getSubjectId() + ",'" +
		 * sgs.getSubjectName() + "'," + 0 + ")"; stmt.executeUpdate(query); }
		 * catch (SQLException e) { e.printStackTrace(); } } } }
		 */

		for (Section section : sections) {
			try {
				stmt.executeUpdate("delete from subject_teacher where SectionId=" + section.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "insert into subject_teacher(SectionId, SubjectId, SubjectName) "
					+ "(select A.Id as SectionId, C.SubjectId as SubjectId, C.SubjectName as SubjectName "
					+ "from section A, class_subject_group B, subject_group_subject C "
					+ "where A.ClassId = B.ClassId and B.SubjectGroupId = C.SubjectGroupId and A.Id = "
					+ section.getId() + ")";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
