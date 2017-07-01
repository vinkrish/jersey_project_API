package com.aanglearning.resource.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.service.app.SMSService;

@Path("/sms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SMSResource {
	
	SMSService service = new SMSService();
	
	@Secured
	@POST
	@Path("class")
	public void sendClassPswd(long classId) {
		service.sendClassPswd(classId);
	}
	
	@Secured
	@POST
	@Path("section")
	public void sendSectionPswd(long sectionId) {
		service.sendSectionPswd(sectionId);
	}
	
	@Secured
	@POST
	@Path("student")
	public void sendStudentPswd(long studentId) {
		service.sendStudentPswd(studentId);
	}
	
	@POST
	@Path("student/{username}")
	public void sendStudentUserPswd(@PathParam("username") String username) {
		service.sendStudentUserPassword(username);
	}
	
	@Secured
	@POST
	@Path("teachers/school")
	public void sendTeachersPswd(long schoolId) {
		service.sendTeachersPswd(schoolId);
	}
	
	@Secured
	@POST
	@Path("teacher")
	public void sendTeacherPswd(long teacherId) {
		service.sendTeacherPswd(teacherId);
	}
	
	@POST
	@Path("teacher/{username}")
	public void sendTeacherUserPswd(@PathParam("username") String username) {
		service.sendTeacherUserPassword(username);
	}
	
	@POST
	@Path("principal/{username}")
	public void sendPrincipalUserPswd(@PathParam("username") String username) {
		service.sendPrincipalUserPassword(username);
	}

}
