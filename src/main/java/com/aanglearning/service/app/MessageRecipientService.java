package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.SQLException;

import com.aanglearning.service.DatabaseUtil;

public class MessageRecipientService {
	Connection connection = null;

	public MessageRecipientService() throws SQLException {
		connection = DatabaseUtil.getConnection();
	}
}
