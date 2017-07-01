package com.aanglearning.resource.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
	@GET
	@Path("class/{classId}")
	public void sendClassPswd(@PathParam("classId") long classId) {
		service.sendClassPswd(classId);
	}
	
	@Secured
	@GET
	@Path("section/{sectionId}")
	public void sendSectionPswd(@PathParam("sectionId") long sectionId) {
		service.sendSectionPswd(sectionId);
	}
	
	@Secured
	@GET
	@Path("student/{studentId}")
	public void sendStudentPswd(@PathParam("studentId") long studentId) {
		service.sendStudentPswd(studentId);
	}
	
	@GET
	@Path("student/user/{username}")
	public void sendStudentUserPswd(@PathParam("username") String username) {
		service.sendStudentUserPassword(username);
	}
	
	@Secured
	@GET
	@Path("teachers/{schoolId}")
	public void sendTeachersPswd(@PathParam("schoolId") long schoolId) {
		service.sendTeachersPswd(schoolId);
	}
	
	@Secured
	@GET
	@Path("teacher/{teacherId}")
	public void sendTeacherPswd(@PathParam("teacherId") long teacherId) {
		service.sendTeacherPswd(teacherId);
	}
	
	@GET
	@Path("teacher/user/{username}")
	public void sendTeacherUserPswd(@PathParam("username") String username) {
		service.sendTeacherUserPassword(username);
	}
	
	@GET
	@Path("principal/user/{username}")
	public void sendPrincipalUserPswd(@PathParam("username") String username) {
		service.sendPrincipalUserPassword(username);
	}

}
