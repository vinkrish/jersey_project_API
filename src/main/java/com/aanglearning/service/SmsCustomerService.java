package com.aanglearning.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aanglearning.model.Client;
import com.aanglearning.model.SmsCustomer;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SmsCustomerService {
	AmazonSNSClient snsClient = new AmazonSNSClient();

	Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
	
	ClientService clientService = new ClientService();
	
	Connection connection;
	
	public SmsCustomerService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String createSNSTopic(AmazonSNSClient snsClient) {
		CreateTopicRequest createTopic = new CreateTopicRequest("smsTopic");
		CreateTopicResult result = snsClient.createTopic(createTopic);
		return result.getTopicArn();
	}
	
	private void add(SmsCustomer smsCustomer) {
		String query = "insert into sms_customer(Message, ClientId, SentTo, SentTime) "
				+ "values (?,?,?,?)";
		try{
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setString(1, smsCustomer.getMessage());
	    	preparedStatement.setLong(2, smsCustomer.getClientId());
	    	preparedStatement.setString(3, smsCustomer.getSentTo());
	    	preparedStatement.setLong(4, smsCustomer.getSentTime());
	    	preparedStatement.executeUpdate();
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public Client smsAllCustomer(final SmsCustomer smsCustomer) {
		add(smsCustomer);
		Client client = clientService.getClient(smsCustomer.getClientId());
		final List<String> customers = getAllCustomers();
		int totalCount = client.getSmsCount() + customers.size();
		final int smsCount;
		if(totalCount > client.getSmsCredentials()) {
			smsCount = client.getSmsCredentials() - client.getSmsCount();
			client.setSmsCount(client.getSmsCredentials());
		} else {
			smsCount = customers.size();
			client.setSmsCount(totalCount);
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				String topicArn = createSNSTopic(snsClient);
				for(int i=0; i<smsCount; i++) {
					snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + customers.get(i)));
				}

				smsAttributes.put("AWS.SNS.SMS.SMSType",
						new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

				snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(smsCustomer.getMessage())
						.withMessageAttributes(smsAttributes));

			}
		}).start();
		
		return client;
	}
	
	public Client smsMultipleCustomer(final SmsCustomer smsCustomer) {
		add(smsCustomer);
		Client client = clientService.getClient(smsCustomer.getClientId());
		final String[] customers = smsCustomer.getSentTo().split(",");
		int totalCount = client.getSmsCount() + customers.length;
		final int smsCount;
		if(totalCount > client.getSmsCredentials()) {
			smsCount = client.getSmsCredentials() - client.getSmsCount();
			client.setSmsCount(client.getSmsCredentials());
		} else {
			smsCount = customers.length;
			client.setSmsCount(totalCount);
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				String topicArn = createSNSTopic(snsClient);
				for(int i=0; i<smsCount; i++) {
					snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + customers[i]));
				}

				smsAttributes.put("AWS.SNS.SMS.SMSType",
						new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

				snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(smsCustomer.getMessage())
						.withMessageAttributes(smsAttributes));

			}
		}).start();
		
		return client;
	}
	
	private List<String> getAllCustomers() {
		String query = "select Mobile from customer";
		List<String> customers = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				customers.add(rs.getString("Mobile"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public List<SmsCustomer> getSms(long clientId) {
		String query = "select * from sms_customer where ClientId = ? order by Id desc LIMIT 50";
		List<SmsCustomer> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, clientId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				SmsCustomer sms = new SmsCustomer();
				sms.setId(rs.getLong("Id"));
				sms.setMessage(rs.getString("Message"));
				sms.setClientId(rs.getLong("ClientId"));
				sms.setSentTo(rs.getString("sentTo"));
				sms.setSentTime(rs.getLong("sentTime"));
				messages.add(sms);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<SmsCustomer> getSmsFromId(long clientId, long id) {
		String query = "select * from sms_customer where ClientId = ? and Id < ? order by Id desc LIMIT 50";
		List<SmsCustomer> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, clientId);
			preparedStatement.setLong(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				SmsCustomer sms = new SmsCustomer();
				sms.setId(rs.getLong("Id"));
				sms.setMessage(rs.getString("Message"));
				sms.setClientId(rs.getLong("ClientId"));
				sms.setSentTo(rs.getString("sentTo"));
				sms.setSentTime(rs.getLong("sentTime"));
				messages.add(sms);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
}
