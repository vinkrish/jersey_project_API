package com.aanglearning.service.exam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.exam.Exam;
import com.aanglearning.service.DatabaseUtil;

public class ExamService {
	Statement stmt;

	public ExamService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Exam> getExamList(long classId) {
		String query = "select * from exam where ClassId = " + classId;
		List<Exam> examList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Exam exam = new Exam();
				exam.setId(rs.getLong("Id"));
				exam.setExamName(rs.getString("ExamName"));
				exam.setClassId(rs.getLong("ClassId"));
				exam.setTerm(rs.getInt("Term"));
				exam.setType(rs.getString("Type"));
				exam.setCalculation(rs.getInt("Calculation"));
				exam.setPercentage(rs.getFloat("Percentage"));
				examList.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examList;
	}
	
	public Exam add(Exam exam) {
		try {
			String query = "insert into exam(Id, ExamName, ClassId, Term, Type, Calculation, Percentage) "
					+ "values ("
					+ exam.getId() + ",'" 
					+ exam.getExamName() + "',"
					+ exam.getClassId() + ","
					+ exam.getTerm() + ",'"
					+ exam.getType() + "',"
					+ exam.getCalculation() + ","
					+ exam.getPercentage() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			exam.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exam;
	}
	
	public void update(Exam exam) {
		try {
			String query = "update exam set ExamName = '" +exam.getExamName() + "', Type='" + exam.getType() + 
					"', Calculation = " + exam.getCalculation() +", Percentage=" + exam.getPercentage() + " where Id=" + exam.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long examId){
		try {
			String query = "delete from exam where Id=" + examId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
