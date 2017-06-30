package com.aanglearning.resource.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.service.app.PasswordService;

@Path("/password")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PasswordResource {
	
	PasswordService service = new PasswordService();
	
	@Secured
	@GET
	@Path("class/{classId}")
	public void updateClassPswd(@PathParam("classId") long classId) {
		service.updateClassPswd(classId);
	}
	
	@Secured
	@GET
	@Path("section/{sectionId}")
	public void updateSectionPswd(@PathParam("sectionId") long sectionId) {
		service.updateSectionPswd(sectionId);
	}
	
	@Secured
	@GET
	@Path("student/{studentId}")
	public void updateStudentPswd(@PathParam("studentId") long studentId) {
		service.updateStudentPswd(studentId);
	}
	
	@Secured
	@GET
	@Path("teachers/{schoolId}")
	public void updateTeachersPswd(@PathParam("schoolId") long schoolId) {
		service.updateTeachersPswd(schoolId);
	}
	
	@Secured
	@GET
	@Path("teacher/{teacherId}")
	public void updateTeacherPswd(@PathParam("teacherId") long teacherId) {
		service.updateTeacherPswd(teacherId);
	}
}
