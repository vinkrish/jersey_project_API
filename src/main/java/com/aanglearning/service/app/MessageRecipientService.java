package com.aanglearning.service.app;

import java.sql.Connection;

import com.aanglearning.service.JDBC;

public class MessageRecipientService {
	Connection connection = null;

	public MessageRecipientService() {
		connection = JDBC.getConnection();
	}
}
