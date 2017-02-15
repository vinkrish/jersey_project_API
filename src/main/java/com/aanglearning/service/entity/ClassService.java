package com.aanglearning.service.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.entity.Clas;
import com.aanglearning.service.JDBC;

public class ClassService {
	Statement stmt = null;

	public ClassService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Clas> getClassList(long schoolId) {
		String query = "select * from class where SchoolId = " + schoolId;
		List<Clas> classList = new ArrayList<Clas>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Clas clas = new Clas();
				clas.setId(rs.getLong("Id"));
				clas.setClassName(rs.getString("ClassName"));
				clas.setSchoolId(rs.getLong("SchoolId"));
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
			String query = "insert into class(Id, ClassName, SchoolId, AttendanceType) "
					+ "values ("
					+ clas.getId() + ",'" 
					+ clas.getClassName() + "',"
					+ clas.getSchoolId() + ",'" 
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
			+ "' where Id=" + clas.getId();
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