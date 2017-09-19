package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.app.Sms;
import com.aanglearning.model.entity.Clas;
import com.aanglearning.model.entity.Section;
import com.aanglearning.model.entity.Student;
import com.aanglearning.model.entity.Teacher;
import com.aanglearning.service.app.SMSMessageService;

@Path("/smsmessage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SMSMessageResource {
	
	SMSMessageService service = new SMSMessageService();
	
	@Secured
	@POST
	@Path("school/{schoolId}")
	public Sms sendSchoolSMS(@PathParam("schoolId") long schoolId, Sms sms) {
		return service.sendSchoolSMS(schoolId, sms);
	}
	
	@Secured
	@POST
	@Path("class/{classId}")
	public Sms sendClassSMS(@PathParam("classId") long classId, Sms sms) {
		return service.sendClassSMS(classId, sms);
	}
	
	@Secured
	@POST
	@Path("classes")
	public Sms sendClassesSMS(List<Clas> classes, Sms sms) {
		return service.sendClassesSMS(classes, sms);
	}
	
	@Secured
	@POST
	@Path("section/{sectionId}")
	public Sms sendSectionSMS(@PathParam("sectionId") long sectionId, Sms sms) {
		return service.sendSectionSMS(sectionId, sms);
	}
	
	@Secured
	@POST
	@Path("sections")
	public Sms sendSectionsSMS(List<Section> sections, Sms sms) {
		return service.sendSectionsSMS(sections, sms);
	}
	
	@Secured
	@POST
	@Path("school/{schoolId}/male")
	public Sms sendMaleSMS(@PathParam("schoolId") long schoolId, Sms sms) {
		return service.sendGenderSMS(schoolId, sms, "M");
	}
	
	@Secured
	@POST
	@Path("school/{schoolId}/female")
	public Sms sendFemaleSMS(@PathParam("schoolId") long schoolId, Sms sms) {
		return service.sendGenderSMS(schoolId, sms, "F");
	}
	
	@Secured
	@POST
	@Path("students")
	public Sms sendStudentSMS(List<Student> students, Sms sms) {
		return service.sendStudentSMS(students, sms);
	}
	
	@Secured
	@POST
	@Path("teachers")
	public Sms sendTeacherSMS(List<Teacher> teachers, Sms sms) {
		return service.sendTeacherSMS(teachers, sms);
	}

}
