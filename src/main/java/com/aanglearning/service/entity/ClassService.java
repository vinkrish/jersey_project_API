package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aanglearning.model.entity.Clas;
import com.aanglearning.service.DatabaseUtil;

public class ClassService {
	Statement stmt;

	public ClassService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Clas> getClassList(long schoolId) {
		String query = "select * from class where SchoolId = " + schoolId;
		return getClasses(query);
	}
	
	public List<Clas> getSectionTeacherClasses(long teacherId) {
		String query = "select * from class where Id in (select ClassId from section where TeacherId = " + teacherId + ")";
		return getClasses(query);
	}
	
	public List<Clas> getSubjectTeacherClasses(long teacherId) {
		String query1 = "select * from class where Id in (select ClassId from section where Id in "
				+ "(select SectionId from subject_teacher where TeacherId="+teacherId+" group by SectionId))";
		String query2 = "select * from class where Id in (select ClassId from section where TeacherId = " + teacherId + ")";
		String query3 = "select * from class where TeacherId = " + teacherId;
		Set<Clas> set = new HashSet<Clas>(getClasses(query1));
		set.addAll(getClasses(query2));
		set.addAll(getClasses(query3));
		return new ArrayList<Clas>(set);
	}
	
	public List<Clas> getClasses(String query) {
		List<Clas> classList = new ArrayList<Clas>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Clas clas = new Clas();
				clas.setId(rs.getLong("Id"));
				clas.setClassName(rs.getString("ClassName"));
				clas.setSchoolId(rs.getLong("SchoolId"));
				clas.setTeacherId(rs.getLong("TeacherId"));
				clas.setAttendanceType(rs.getString("AttendanceType"));
				classList.add(clas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return classList;
	}
	
	public Clas add(Clas clas) {
		try {
			String query = "insert into class(Id, ClassName, SchoolId, TeacherId, AttendanceType) "
					+ "values ("
					+ clas.getId() + ",'" 
					+ clas.getClassName() + "',"
					+ clas.getSchoolId() + "," 
					+ clas.getTeacherId() + ",'"
					+ clas.getAttendanceType() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			clas.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clas;
	}
	
	public void update(Clas clas) {
		try {
			String query = "update class set ClassName='"+clas.getClassName() + "', AttendanceType='" + clas.getAttendanceType() 
			+ "', TeacherId = " + clas.getTeacherId() + " where Id = " + clas.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long classId){
		try {
			String query = "delete from class where Id=" + classId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
