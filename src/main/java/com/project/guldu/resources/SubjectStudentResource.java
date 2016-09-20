package com.project.guldu.resources;

import java.util.List;

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
	public SubjectStudent getSubjectStudent(@PathParam("sectionId") long sectionId,
			@PathParam("subjectId") long subjectId) {
		return subjectStudentService.getSubjectStudents(sectionId, subjectId);
	}
	
	@Secured
	@GET
	@Path("section/{sectionId}/subjectGroup/{subjectGroupId}")
	public List<SubjectStudent> getSubjectStudentsByGroupId(@PathParam("sectionId") long sectionId,
			@PathParam("subjectGroupId") long subjectGroupId) {
		return subjectStudentService.getSubjectStudentsList(sectionId, subjectGroupId);
	}
	
	@Secured
	@POST
	public void add(List<SubjectStudent> subjectStudents) {
		subjectStudentService.add(subjectStudents);
	}
	
	@Secured
	@PUT
	public void update(List<SubjectStudent> subjectStudents) {
		subjectStudentService.update(subjectStudents);
	}
	
	@Secured
	@POST
	@Path("recent")
	public void recent(List<SubjectStudent> subjectStudent){
		subjectStudentService.recent(subjectStudent);
	}

}
