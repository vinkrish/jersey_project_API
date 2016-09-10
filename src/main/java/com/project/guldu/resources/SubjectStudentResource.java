package com.project.guldu.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.guldu.model.SubjectStudent;
import com.project.guldu.service.SubjectStudentService;

import authentication.Secured;

@Path("/subjectstudent")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectStudentResource {
	
	SubjectStudentService subjectStudentService = new SubjectStudentService();
	
	@Secured
	@GET
	@Path("section/{sectionId}/subject/{subjectId}")
	public SubjectStudent getSubjectGroupList(@PathParam("sectionId") long sectionId,
			@PathParam("subjectId") long subjectId) {
		return subjectStudentService.getSubjectStudents(sectionId, subjectId);
	}
	
	@Secured
	@POST
	public SubjectStudent add(SubjectStudent subjectStudent) {
		return subjectStudentService.add(subjectStudent);
	}
	
	@Secured
	@PUT
	@Path("/{subjectStudentId}")
	public void update(SubjectStudent subjectStudent) {
		subjectStudentService.update(subjectStudent);
	}

}
