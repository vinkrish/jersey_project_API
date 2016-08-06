package com.project.guldu.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.guldu.model.Subject;
import com.project.guldu.service.SubjectService;

import authentication.Secured;

@Path("/subject")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectResource {
	
	SubjectService subjectService = new SubjectService();
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<Subject> getSubjectList(@PathParam("schoolId") long schoolId) {
		return subjectService.getSubjectList(schoolId);
	}
	
	@Secured
	@POST
	@Path("list")
	public void addSubject(String subjectStr) {
		subjectService.addSubject(subjectStr);
	}
	
	@Secured
	@POST
	public Subject add(Subject subject) {
		return subjectService.add(subject);
	}
	
	@Secured
	@PUT
	@Path("/{subjectId}")
	public void update(Subject subject) {
		subjectService.update(subject);
	}
	
	@Secured
	@DELETE
	@Path("/{subjectId}")
	public void delete(@PathParam("subjectId") long subjectId) {
		subjectService.delete(subjectId);
	}
	
}
