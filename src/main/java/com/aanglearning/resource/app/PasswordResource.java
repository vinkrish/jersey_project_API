package com.aanglearning.resource.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	@Path("class")
	public void updateClassPswd(long classId) {
		service.updateClassPswd(classId);
	}
	
	@Secured
	@POST
	@Path("section")
	public void updateSectionPswd(long sectionId) {
		service.updateSectionPswd(sectionId);
	}
	
	@Secured
	@POST
	@Path("student")
	public void updateStudentPswd(long studentId) {
		service.updateStudentPswd(studentId);
	}
	
	@Secured
	@POST
	@Path("teachers/school")
	public void updateTeachersPswd(long schoolId) {
		service.updateTeachersPswd(schoolId);
	}
	
	@Secured
	@POST
	@Path("teacher")
	public void updateTeacherPswd(long teacherId) {
		service.updateTeacherPswd(teacherId);
	}
}
