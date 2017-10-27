package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.app.Sms;
import com.aanglearning.model.app.SmsClass;
import com.aanglearning.model.app.SmsSection;
import com.aanglearning.model.app.SmsStudent;
import com.aanglearning.model.app.SmsTeacher;
import com.aanglearning.service.app.SMSMessageService;

@Path("/smsmessage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SMSMessageResource {
	
	SMSMessageService service = new SMSMessageService();
	
	@Secured
	@POST
	@Path("school")
	public Sms sendSchoolSMS(Sms sms) {
		return service.sendSchoolSMS(sms.getSchoolId(), sms);
	}
	
	@Secured
	@POST
	@Path("allstudents")
	public Sms sendAllStudentsSMS(Sms sms) {
		return service.sendAllStudentsSMS(sms.getSchoolId(), sms);
	}
	
	@Secured
	@POST
	@Path("class")
	public Sms sendClassSMS(Sms sms) {
		return service.sendClassSMS(sms.getClassId(), sms);
	}
	
	@Secured
	@POST
	@Path("classes")
	public Sms sendClassesSMS(SmsClass smsClass) {
		return service.sendClassesSMS(smsClass.getClasses(), smsClass.getSms());
	}
	
	@Secured
	@POST
	@Path("section")
	public Sms sendSectionSMS(Sms sms) {
		return service.sendSectionSMS(sms.getSectionId(), sms);
	}
	
	@Secured
	@POST
	@Path("sections")
	public Sms sendSectionsSMS(SmsSection smsSection) {
		return service.sendSectionsSMS(smsSection.getSections(), smsSection.getSms());
	}
	
	@Secured
	@POST
	@Path("school/male")
	public Sms sendMaleSMS(Sms sms) {
		return service.sendGenderSMS(sms.getSchoolId(), sms, "M");
	}
	
	@Secured
	@POST
	@Path("school/female")
	public Sms sendFemaleSMS(Sms sms) {
		return service.sendGenderSMS(sms.getSchoolId(), sms, "F");
	}
	
	@Secured
	@POST
	@Path("students")
	public Sms sendStudentSMS(SmsStudent smsStudent) {
		return service.sendStudentSMS(smsStudent.getStudents(), smsStudent.getSms());
	}
	
	@Secured
	@POST
	@Path("teachers")
	public Sms sendTeacherSMS(SmsTeacher smsTeacher) {
		return service.sendTeacherSMS(smsTeacher.getTeachers(), smsTeacher.getSms());
	}
	
	@Secured
	@GET
	@Path("sender/{senderId}/messagesUp/{messageId}")
	public List<Sms> getSMSMessagesAboveId(@PathParam("senderId") long senderId,
			@PathParam("messageId") long messageId) {
		return service.getSMSMessagesAboveId(senderId, messageId);
	}
	
	@Secured
	@GET
	@Path("sender/{senderId}")
	public List<Sms> getSMSMessages(@PathParam("senderId") long senderId) {
		return service.getSMSMessages(senderId);
	}

}
