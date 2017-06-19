package com.aanglearning.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aanglearning.model.Authorization;

public class AuthorizationService {
	Connection connection;

	public AuthorizationService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isTokenValid(String token) {
		String query = "select User from authorization where Token = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, token);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void updateFcmToken(Authorization authorization) {
		String query = "update authorization set FcmToken = ? where User = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, authorization.getFcmToken());
			preparedStatement.setString(2, authorization.getUser());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
