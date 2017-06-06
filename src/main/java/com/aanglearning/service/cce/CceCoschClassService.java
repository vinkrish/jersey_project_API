package com.aanglearning.service.cce;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aanglearning.model.cce.CceCoschClass;
import com.aanglearning.service.DatabaseUtil;

public class CceCoschClassService {
	Statement stmt;

	public CceCoschClassService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CceCoschClass> getCceCoschClasses(long coschId) {
		String query = "select * from cce_coscholastic_class where CoScholasticId = " + coschId;
		List<CceCoschClass> subjectGroupSubjects = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				CceCoschClass csg = new CceCoschClass();
				csg.setId(rs.getLong("Id"));
				csg.setCoscholasticId(rs.getLong("CoScholasticId"));
				csg.setClassId(rs.getLong("ClassId"));
				csg.setClassName(rs.getString("ClassName"));
				subjectGroupSubjects.add(csg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectGroupSubjects;
	}
	
	public CceCoschClass add(CceCoschClass ccc) {
		try {
			String query = "insert into cce_coscholastic_class(Id, CoScholasticId, ClassId, ClassName) "
					+ "values ("
					+ ccc.getId() + "," 
					+ ccc.getCoscholasticId() + ","
					+ ccc.getClassId() + ",'" 
					+ ccc.getClassName() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ccc.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ccc;
	}
	
	public void delete(long id){
		try {
			String query = "delete from cce_coscholastic_class where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
