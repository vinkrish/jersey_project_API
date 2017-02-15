package com.aanglearning.service.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.Mark;
import com.aanglearning.service.JDBC;

public class MarkService {
	Connection connection = null;

	public MarkService() {
		connection = JDBC.getConnection();
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
				mark.setStudentId(rs.getLong("StudentId"));
				mark.setMark(rs.getFloat("Mark"));
				mark.setGrade(rs.getString("Grade"));
				marks.add(mark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//JDBC.closeConnection();
		}
		return marks;
	}

	public void add(List<Mark> marks) {
		String query = "insert into mark(Id, ExamId, SubjectId, SectionId, StudentId, Mark, Grade) values (?,?,?,?,?,?,?)";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(Mark mark: marks) {
		    	preparedStatement.setLong(1, mark.getId());
		    	preparedStatement.setLong(2, mark.getExamId());
		    	preparedStatement.setLong(3, mark.getSubjectId());
		    	preparedStatement.setLong(4, mark.getSectionId());
		    	preparedStatement.setLong(5, mark.getStudentId());
		    	preparedStatement.setFloat(6, mark.getMark());
		    	preparedStatement.setString(7, mark.getGrade());
		    	preparedStatement.executeUpdate();
		    }
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			//JDBC.closeConnection();
		}
	}

	public void update(List<Mark> marks) {
		String query = "update mark set Mark=?, Grade=? where Id=?";
		try{
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    for(Mark mark: marks) {
		    	preparedStatement.setFloat(1, mark.getMark());
		    	preparedStatement.setString(2, mark.getGrade());
		    	preparedStatement.setLong(3, mark.getId());
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
		    connection.setAutoCommit(false);
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    	preparedStatement.setLong(1, examId);
		    	preparedStatement.setLong(2, subjectId);
		    	preparedStatement.setLong(3, sectionId);
		    	preparedStatement.executeUpdate();
		    connection.commit();
		} catch(Exception e) {
		    try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
