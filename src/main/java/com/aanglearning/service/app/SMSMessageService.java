package com.aanglearning.service.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aanglearning.model.app.Sms;
import com.aanglearning.model.entity.Clas;
import com.aanglearning.model.entity.Section;
import com.aanglearning.model.entity.Student;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.service.DatabaseUtil;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SMSMessageService {
	AmazonSNSClient snsClient = new AmazonSNSClient();

	Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();

	Connection connection;
	Statement stmt;

	public SMSMessageService() {
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Sms add(Sms sms) {
		String query = "insert into sms_info(SchoolId, SenderId, SenderName, SentTime, Message, SentTo) "
				+ "values (?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, sms.getSchoolId());
			preparedStatement.setLong(2, sms.getSenderId());
			preparedStatement.setString(3, sms.getSenderName());
			preparedStatement.setLong(4, sms.getSentTime());
			preparedStatement.setString(5, sms.getSentTo());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			long pk = 0;
			if (rs.next()) {
				pk = rs.getLong(1);
			}
			sms.setId(pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sms;
	}

	public String createSNSTopic(AmazonSNSClient snsClient) {
		CreateTopicRequest createTopic = new CreateTopicRequest("shikshithaTopic");
		CreateTopicResult result = snsClient.createTopic(createTopic);
		return result.getTopicArn();
	}

	public Sms sendSchoolSMS(final long schoolId, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Long> sectionList = new ArrayList<>();
				try {
					String query = "select Id from section where ClassId in (select Id from class where SchoolId = ?)";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setLong(1, schoolId);
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						sectionList.add(rs.getLong("Id"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				for (Long section : sectionList) {
					String topicArn = createSNSTopic(snsClient);
					try {
						String query = "select Name, Username from student where SectionId = ? and Username != ''";
						PreparedStatement preparedStatement = connection.prepareStatement(query);
						preparedStatement.setLong(1, section);
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + rs.getString("Username")));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					smsAttributes.put("AWS.SNS.SMS.SMSType",
							new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

					snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(sms.getMessage())
							.withMessageAttributes(smsAttributes));

				}
			}
		}).start();

		return savedSms;
	}

	public Sms sendClassSMS(final long classId, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Long> sectionList = new ArrayList<>();
				try {
					String query = "select Id from section where ClassId = ?";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setLong(1, classId);
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						sectionList.add(rs.getLong("Id"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				for (Long section : sectionList) {
					String topicArn = createSNSTopic(snsClient);
					try {
						String query = "select Name, Username from student where SectionId = ? and Username != ''";
						PreparedStatement preparedStatement = connection.prepareStatement(query);
						preparedStatement.setLong(1, section);
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + rs.getString("Username")));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					smsAttributes.put("AWS.SNS.SMS.SMSType",
							new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

					snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(sms.getMessage())
							.withMessageAttributes(smsAttributes));

				}
			}
		}).start();

		return savedSms;
	}
	
	public Sms sendClassesSMS(final List<Clas> classes, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Long> sectionList = new ArrayList<>();
				for(Clas clas: classes) {
					try {
						String query = "select Id from section where ClassId = ?";
						PreparedStatement preparedStatement = connection.prepareStatement(query);
						preparedStatement.setLong(1, clas.getId());
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							sectionList.add(rs.getLong("Id"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				for (Long section : sectionList) {
					String topicArn = createSNSTopic(snsClient);
					try {
						String query = "select Name, Username from student where SectionId = ? and Username != ''";
						PreparedStatement preparedStatement = connection.prepareStatement(query);
						preparedStatement.setLong(1, section);
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + rs.getString("Username")));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					smsAttributes.put("AWS.SNS.SMS.SMSType",
							new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

					snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(sms.getMessage())
							.withMessageAttributes(smsAttributes));

				}
			}
		}).start();

		return savedSms;
	}

	public Sms sendSectionSMS(final long sectionId, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String topicArn = createSNSTopic(snsClient);
				try {
					String query = "select Name, Username from student where SectionId = ? and Username != ''";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setLong(1, sectionId);
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + rs.getString("Username")));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				smsAttributes.put("AWS.SNS.SMS.SMSType",
						new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

				snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(sms.getMessage())
						.withMessageAttributes(smsAttributes));

			}
		}).start();

		return savedSms;
	}
	
	public Sms sendSectionsSMS(final List<Section> sections, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String topicArn = createSNSTopic(snsClient);

				for(Section section: sections) {
					try {
						String query = "select Name, Username from student where SectionId = ? and Username != ''";
						PreparedStatement preparedStatement = connection.prepareStatement(query);
						preparedStatement.setLong(1, section.getId());
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + rs.getString("Username")));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					smsAttributes.put("AWS.SNS.SMS.SMSType",
							new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

					snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(sms.getMessage())
							.withMessageAttributes(smsAttributes));
				}

			}
		}).start();

		return savedSms;
	}
	
	public Sms sendGenderSMS(final long schoolId, final Sms sms, final String gender) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Long> sectionList = new ArrayList<>();
				try {
					String query = "select Id from section where ClassId in (select Id from class where SchoolId = ?)";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setLong(1, schoolId);
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						sectionList.add(rs.getLong("Id"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				for (Long section : sectionList) {
					String topicArn = createSNSTopic(snsClient);
					try {
						String query = "select Name, Username from student where SectionId = ? and Username != '' and Gender = ?";
						PreparedStatement preparedStatement = connection.prepareStatement(query);
						preparedStatement.setLong(1, section);
						preparedStatement.setString(2, gender);
						ResultSet rs = preparedStatement.executeQuery();
						while (rs.next()) {
							snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + rs.getString("Username")));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					smsAttributes.put("AWS.SNS.SMS.SMSType",
							new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

					snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(sms.getMessage())
							.withMessageAttributes(smsAttributes));

				}
			}
		}).start();

		return savedSms;
	}
	
	public Sms sendStudentSMS(final List<Student> students, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String topicArn = createSNSTopic(snsClient);
				for(Student student: students) {
					snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + student.getUsername()));
				}

				smsAttributes.put("AWS.SNS.SMS.SMSType",
						new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

				snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(sms.getMessage())
						.withMessageAttributes(smsAttributes));

			}
		}).start();

		return savedSms;
	}
	
	public Sms sendTeacherSMS(final List<Teacher> teachers, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String topicArn = createSNSTopic(snsClient);
				for(Teacher teacher: teachers) {
					snsClient.subscribe(new SubscribeRequest(topicArn, "sms", "91" + teacher.getUsername()));
				}

				smsAttributes.put("AWS.SNS.SMS.SMSType",
						new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

				snsClient.publish(new PublishRequest().withTopicArn(topicArn).withMessage(sms.getMessage())
						.withMessageAttributes(smsAttributes));

			}
		}).start();

		return savedSms;
	}

}
