package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.guldu.model.SliptestPortion;

public class SliptestPortionService {
	Statement stmt = null;

	public SliptestPortionService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<SliptestPortion> getSliptestPortions(long sliptestId) {
		String query = "select * from sliptest_portion where SliptestId = " + sliptestId;
		List<SliptestPortion> sliptestPortions = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				SliptestPortion csg = new SliptestPortion();
				csg.setId(rs.getLong("Id"));
				csg.setSliptestId(rs.getLong("SliptestId"));
				csg.setPortionId(rs.getLong("PortionId"));
				csg.setPortionName(rs.getString("PortionName"));
				sliptestPortions.add(csg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sliptestPortions;
	}
	
	public SliptestPortion add(SliptestPortion sp) {
		try {
			String query = "insert into sliptest_portion(Id, SliptestId, PortionId, PortionName) "
					+ "values ("
					+ sp.getId() + "," 
					+ sp.getSliptestId() + ","
					+ sp.getPortionId() + ",'" 
					+ sp.getPortionName() + "')"; 
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			sp.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sp;
	}
	
	public void delete(long id){
		try {
			String query = "delete from sliptest_portion where Id=" + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
