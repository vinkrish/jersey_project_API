package com.aanglearning.service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.aanglearning.model.entity.Clas;
import com.aanglearning.model.entity.Section;
import com.aanglearning.service.entity.SectionService;

public class SharedService {
	Statement stmt;
	SectionService sectionService;

	public SharedService() {
		sectionService = new SectionService();
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertSubjectTeacher(Clas clas) {
		List<Section> sections = sectionService.getSectionList(clas.getId());

		for (Section section : sections) {
			try {
				stmt.executeUpdate("delete from subject_teacher where SectionId=" + section.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "insert into subject_teacher(SectionId, ClassId, SubjectId, SubjectName) "
					+ "(select A.Id as SectionId, A.ClassId as ClassId, C.SubjectId as SubjectId, C.SubjectName as SubjectName "
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
