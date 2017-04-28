package com.aanglearning.service.exam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.GradeClassWise;
import com.aanglearning.service.JDBC;

public class GradeClassWiseService {
	Statement stmt;

	public GradeClassWiseService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<GradeClassWise> getGradesClassWise(long classId) {
		String query = "select * from grade_class_wise where ClassId = " + classId;
		List<GradeClassWise> gcwList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				GradeClassWise gcw = new GradeClassWise();
				gcw.setId(rs.getLong("Id"));
				gcw.setClassId(rs.getLong("ClassId"));
				gcw.setGrade(rs.getString("Grade"));
				gcw.setMarkFrom(rs.getInt("MarkFrom"));
				gcw.setMarkTo(rs.getInt("MarkTo"));
				gcw.setGradePoint(rs.getInt("GradePoint"));
				gcwList.add(gcw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gcwList;
	}
	
	public GradeClassWise add(GradeClassWise gcw) {
		try {
			String query = "insert into grade_class_wise(Id, ClassId, Grade, MarkFrom, MarkTo, GradePoint) "
					+ "values ("
					+ gcw.getId() + "," 
					+ gcw.getClassId() + ",'"
					+ gcw.getGrade() + "',"
					+ gcw.getMarkFrom() + ","
					+ gcw.getMarkTo() + ","
					+ gcw.getGradePoint() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			gcw.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gcw;
	}
	
	public void update(GradeClassWise gcw) {
		try {
			String query = "update grade_class_wise set "
					+ "Grade = '" + gcw.getGrade()
					+ "', MarkFrom = " + gcw.getMarkFrom() 
					+ ", MarkTo = " + gcw.getMarkTo() 
					+ ", GradePoint = " + gcw.getGradePoint()
					+ " where Id = " + gcw.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id){
		try {
			String query = "delete from exam_subject where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
