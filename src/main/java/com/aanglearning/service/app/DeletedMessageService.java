package com.aanglearning.service.app;

import java.sql.SQLException;
import java.sql.Statement;

import com.aanglearning.service.JDBC;

public class DeletedMessageService {
	
	Statement stmt = null;

	public DeletedMessageService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
