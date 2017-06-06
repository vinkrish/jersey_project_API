package com.aanglearning.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthorizationService {
	Statement stmt;

	public AuthorizationService() {
		try {
			stmt = DatabaseUtil.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isTokenValid(String token) {
		String query = "select User from authorization where Token = '" + token + "'";
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
