package com.aanglearning.service.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.Mark;
import com.aanglearning.service.DatabaseUtil;

public class MarkService {
	Connection connection;

	public MarkService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Mark> getMarks(long examId, long subjectId, long sectionId) {
		String query = "select * from mark where ExamId=? and SubjectId=? and SectionId=?";
		List<Mark> marks = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, examId);
	    	preparedStatement.setLong(2, subjectId);
	    	preparedStatement.setLong(3, sectionId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Mark mark = new Mark();
				mark.setId(rs.getLong("Id"));
				mark.setExamId(rs.getLong("ExamId"));
				mark.setSubjectId(rs.getLong("SubjectId"));
				mark.setSectionId(rs.getLong("SectionId"));
				mark.setRollNo(rs.getInt("RollNo"));
				mark.setStudentId(rs.getLong("StudentId"));
				mark.setStudentName(rs.getString("StudentName"));
				mark.setMark(rs.getFloat("Mark"));
				mark.setGrade(rs.getString("Grade"));
				marks.add(mark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marks;
	}
	
	public Mark getMark(long examId, long subjectId, long sectionId, long studentId) {
		String query = "select * from mark where ExamId=? and SubjectId=? and SectionId=? and StudentId=?";
		Mark mark = new Mark();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, examId);
	    	preparedStatement.setLong(2, subjectId);
	    	preparedStatement.setLong(3, sectionId);
	    	preparedStatement.setLong(4, studentId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				mark.setId(rs.getLong("Id"));
				mark.setExamId(rs.getLong("ExamId"));
				mark.setSubjectId(rs.getLong("SubjectId"));
				mark.setSectionId(rs.getLong("SectionId"));
				mark.setRollNo(rs.getInt("RollNo"));
				mark.setStudentId(rs.getLong("StudentId"));
				mark.setStudentName(rs.getString("StudentName"));
				mark.setMark(rs.getFloat("Mark"));
				mark.setGrade(rs.getString("Grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mark;
	}

	public void add(List<Mark> marks) {
		String query = "insert into mark(Id, ExamId, SubjectId, SectionId, RollNo, StudentId, StudentName, Mark, Grade) values (?,?,?,?,?,?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(Mark mark: marks) {
		    	preparedStatement.setLong(1, mark.getId());
		    	preparedStatement.setLong(2, mark.getExamId());
		    	preparedStatement.setLong(3, mark.getSubjectId());
		    	preparedStatement.setLong(4, mark.getSectionId());
		    	preparedStatement.setInt(5, mark.getRollNo());
		    	preparedStatement.setLong(6, mark.getStudentId());
		    	preparedStatement.setString(7, mark.getStudentName());
		    	preparedStatement.setFloat(8, mark.getMark());
		    	preparedStatement.setString(9, mark.getGrade());
		    	preparedStatement.executeUpdate();
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update(List<Mark> marks) {
		String query = "update mark set Mark=?, Grade=? where Id=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(Mark mark: marks) {
		    	preparedStatement.setFloat(1, mark.getMark());
		    	preparedStatement.setString(2, mark.getGrade());
		    	preparedStatement.setLong(3, mark.getId());
		    	preparedStatement.executeUpdate();
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*finally {
		    if(connection != null) {
		        try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
		}*/
	}
	
	public void delete(long examId, long subjectId, long sectionId) {
		String query = "delete from mark where ExamId=? and SubjectId=? and SectionId=?";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setLong(1, examId);
	    	preparedStatement.setLong(2, subjectId);
	    	preparedStatement.setLong(3, sectionId);
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
