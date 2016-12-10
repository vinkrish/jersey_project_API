package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.CceCoscholastic;

public class CceCoscholasticService {
	Statement stmt = null;

	public CceCoscholasticService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CceCoscholastic> getCceCoscholastics(long schoolId) {
		String query = "select * from cce_coscholastic where SchoolId = " + schoolId;
		List<CceCoscholastic> CceCoSchList = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				CceCoscholastic CceCoSch = new CceCoscholastic();
				CceCoSch.setId(rs.getLong("Id"));
				CceCoSch.setName(rs.getString("Name"));
				CceCoSch.setSchoolId(rs.getLong("SchoolId"));
				CceCoSchList.add(CceCoSch);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CceCoSchList;
	}
	
	public CceCoscholastic add(CceCoscholastic CceCoSch) {
		try {
			String query = "insert into cce_coscholastic(Id, Name, SchoolId) "
					+ "values ("
					+ CceCoSch.getId() + ",'" 
					+ CceCoSch.getName() + "',"
					+ CceCoSch.getSchoolId() + ")";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			CceCoSch.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CceCoSch;
	}
	
	public void update(CceCoscholastic CceCoSch) {
		try {
			String query = "update cce_coscholastic set Name='" + CceCoSch.getName() 
			+ "' where Id=" + CceCoSch.getId();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long classId){
		try {
			String query = "delete from cce_coscholastic where Id=" + classId;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
