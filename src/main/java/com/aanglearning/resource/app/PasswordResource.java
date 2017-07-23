package com.aanglearning.resource.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
	@POST
	@Path("school/{schoolId}")
	public void updateSchoolPswd(@PathParam("schoolId") long schoolId) {
		service.updateSchoolPswd(schoolId);
	}
	
	@Secured
	@POST
	@Path("class/{classId}")
	public void updateClassPswd(@PathParam("classId") long classId) {
		service.updateClassPswd(classId);
	}
	
	@Secured
	@POST
	@Path("section/{sectionId}")
	public void updateSectionPswd(@PathParam("sectionId") long sectionId) {
		service.updateSectionPswd(sectionId);
	}
	
	@Secured
	@POST
	@Path("student/{studentId}")
	public void updateStudentPswd(@PathParam("studentId") long studentId) {
		service.updateStudentPswd(studentId);
	}
	
	@Secured
	@POST
	@Path("teachers/school/{schoolId}")
	public void updateTeachersPswd(@PathParam("schoolId") long schoolId) {
		service.updateTeachersPswd(schoolId);
	}
	
	@Secured
	@POST
	@Path("teacher/{teacherId}")
	public void updateTeacherPswd(@PathParam("teacherId") long teacherId) {
		service.updateTeacherPswd(teacherId);
	}
}
