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
		String query = "insert into sms_info(SchoolId, ClassId, SectionId, SenderId, SenderName, SentTime, Message, SentTo, RecipientRole) "
				+ "values (?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, sms.getSchoolId());
			preparedStatement.setLong(2, sms.getClassId());
			preparedStatement.setLong(3, sms.getSectionId());
			preparedStatement.setLong(4, sms.getSenderId());
			preparedStatement.setString(5, sms.getSenderName());
			preparedStatement.setLong(6, sms.getSentTime());
			preparedStatement.setString(7, sms.getMessage());
			preparedStatement.setString(8, sms.getSentTo());
			preparedStatement.setString(9, sms.getRecipientRole());
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
	
	public List<Sms> getSMSMessagesAboveId(long senderId, long messageId) {
		String query = "select * from sms_info where SenderId = ? and Id > ? order by Id desc";
		List<Sms> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, senderId);
			preparedStatement.setLong(2, messageId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Sms message = new Sms();
				message.setId(rs.getLong("Id"));
				message.setSchoolId(rs.getLong("SchoolId"));
				message.setClassId(rs.getLong("ClassId"));
				message.setSectionId(rs.getLong("SectionId"));
				message.setSenderId(rs.getLong("SenderId"));
				message.setSenderName(rs.getString("SenderName"));
				message.setSentTime(rs.getLong("SentTime"));
				message.setMessage(rs.getString("Message"));
				message.setSentTo(rs.getString("SentTo"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<Sms> getSMSMessages(long senderId) {
		String query = "select * from sms_info where SenderId = ? order by Id desc";
		List<Sms> messages = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, senderId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				Sms message = new Sms();
				message.setId(rs.getLong("Id"));
				message.setSchoolId(rs.getLong("SchoolId"));
				message.setClassId(rs.getLong("ClassId"));
				message.setSectionId(rs.getLong("SectionId"));
				message.setSenderId(rs.getLong("SenderId"));
				message.setSenderName(rs.getString("SenderName"));
				message.setSentTime(rs.getLong("SentTime"));
				message.setMessage(rs.getString("Message"));
				message.setSentTo(rs.getString("SentTo"));
				message.setRecipientRole(rs.getString("RecipientRole"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	private void updateNoOfSms(String query, long schoolId, int count) {
		int noOfSms = 0;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select NumberOfSms from school where Id = " + schoolId);
			while (rs.next()) {
				noOfSms = rs.getInt("NumberOfSms");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(count == 0) {
			try {
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					noOfSms += rs.getInt("count");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			noOfSms += count;
		}
		
		try {
			String sql = "update school set NumberOfSms = " + noOfSms + " where Id = " + schoolId;
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public String createSNSTopic(AmazonSNSClient snsClient) {
		CreateTopicRequest createTopic = new CreateTopicRequest(System.currentTimeMillis() + "");
		CreateTopicResult result = snsClient.createTopic(createTopic);
		return result.getTopicArn();
	}

	public Sms sendSchoolSMS(final long schoolId, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String sql = "select count(*) as count from student where SchoolId = " + sms.getSchoolId();
				updateNoOfSms(sql, sms.getSchoolId(), 0);

				String topicArn = createSNSTopic(snsClient);
				try {
					String query = "select Name, Username from student where SchoolId = ? and Username != ''";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setLong(1, schoolId);
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

	public Sms sendClassSMS(final long classId, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String sql = "select count(*) as count from student where ClassId = " + sms.getClassId();
				updateNoOfSms(sql, sms.getSchoolId(), 0);

				String topicArn = createSNSTopic(snsClient);
				try {
					String query = "select Name, Username from student where ClassId = ? and Username != ''";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setLong(1, classId);
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
	
	public Sms sendClassesSMS(final List<Clas> classes, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				StringBuilder sb = new StringBuilder();
				for(Clas clas: classes) {
					sb.append(clas.getId()).append(",");
				}
				
				String sql = "select count(*) as count from student where ClassId in (" + sb.substring(0, sb.length()-1) + ")";
				updateNoOfSms(sql, sms.getSchoolId(), 0);

				String topicArn = createSNSTopic(snsClient);
				try {
					String query = "select Name, Username from student where ClassId in (" + sb.substring(0, sb.length()-1) + ") and Username != ''";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
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

	public Sms sendSectionSMS(final long sectionId, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String sql = "select count(*) as count from student where SectionId =  " + sms.getSectionId();
				updateNoOfSms(sql, sms.getSchoolId(), 0);
				
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
				StringBuilder sb = new StringBuilder();
				
				for(Section section: sections) {
					sb.append(section.getId()).append(",");
				}
				
				try {
					String query = "select Name, Username from student where SectionId in (" + sb.substring(0, sb.length()-1) + ") and Username != ''";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
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
				
				String sql = "select count(*) as count from student where SectionId in (" + sb.substring(0, sb.length()-1) + ")";
				updateNoOfSms(sql, sms.getSchoolId(), 0);
			}
		}).start();

		return savedSms;
	}
	
	public Sms sendGenderSMS(final long schoolId, final Sms sms, final String gender) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String sql = "select count(*) as count from student where SchoolId = " + sms.getSchoolId() + 
						" and Gender = '" + gender + "'";
				updateNoOfSms(sql, sms.getSchoolId(), 0);

				String topicArn = createSNSTopic(snsClient);
				try {
					String query = "select Name, Username from student where SchoolId = ? and Username != '' and Gender = ?";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setLong(1, schoolId);
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
		}).start();

		return savedSms;
	}
	
	public Sms sendStudentSMS(final List<Student> students, final Sms sms) {
		Sms savedSms = add(sms);

		new Thread(new Runnable() {
			@Override
			public void run() {
				updateNoOfSms("", sms.getSchoolId(), students.size());
				
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
				updateNoOfSms("", sms.getSchoolId(), teachers.size());
				
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
