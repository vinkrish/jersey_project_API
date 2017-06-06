package com.aanglearning.service.cce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.cce.CceAspectGrade;
import com.aanglearning.service.DatabaseUtil;

public class AspectGradeService {
	Connection connection;

	public AspectGradeService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<CceAspectGrade> getGrades(long aspectId, long sectionId, int term) {
		String query = "select * from cce_aspect_grade where AspectId=? and SectionId=? and Term=?";
		List<CceAspectGrade> marks = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, aspectId);
	    	preparedStatement.setLong(2, sectionId);
	    	preparedStatement.setInt(3, term);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				CceAspectGrade grade = new CceAspectGrade();
				grade.setId(rs.getLong("Id"));
				grade.setSectionId(rs.getLong("SectionId"));
				grade.setStudentId(rs.getLong("StudentId"));
				grade.setAspectId(rs.getLong("AspectId"));
				grade.setType(rs.getInt("Type"));
				grade.setTerm(rs.getInt("Term"));
				grade.setGrade(rs.getString("Grade"));
				grade.setValue(rs.getInt("Value"));
				grade.setDescription(rs.getString("Description"));
				marks.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marks;
	}

	public void add(List<CceAspectGrade> grades) {
		
		String query = "insert into cce_aspect_grade(Id, SectionId, StudentId, AspectId, Type, Term, Grade, Value, Description) "
				+ "values (?,?,?,?,?,?,?,?,?)";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(CceAspectGrade grade: grades) {
		    	preparedStatement.setLong(1, grade.getId());
		    	preparedStatement.setLong(2, grade.getSectionId());
		    	preparedStatement.setLong(3, grade.getStudentId());
		    	preparedStatement.setLong(4, grade.getAspectId());
		    	preparedStatement.setInt(5, grade.getType());
		    	preparedStatement.setInt(6, grade.getTerm());
		    	preparedStatement.setString(7, grade.getGrade());
		    	preparedStatement.setInt(8, grade.getValue());
		    	preparedStatement.setString(9, grade.getDescription());
		    	preparedStatement.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void update(List<CceAspectGrade> grades) {
		String query = "update cce_aspect_grade set Type=?, Grade=?, Value=?, Description=? where Id=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(CceAspectGrade grade: grades) {
		    	preparedStatement.setInt(1, grade.getType());
		    	preparedStatement.setString(2, grade.getGrade());
		    	preparedStatement.setInt(3, grade.getValue());
		    	preparedStatement.setString(4, grade.getDescription());
		    	preparedStatement.setLong(5, grade.getId());
		    	preparedStatement.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void delete(long aspectId, long sectionId, int term) {
		String query = "delete from cce_aspect_grade where AspectId=? and SectionId=? and Term=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setLong(1, aspectId);
	    	preparedStatement.setLong(2, sectionId);
	    	preparedStatement.setInt(3, term);
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
