package com.project.guldu.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.guldu.model.SubjectGroupSubject;
import com.project.guldu.service.SubjectGroupSubjectService;

import authentication.Secured;

@Path("/subjectgroupsubject")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectGroupSubjectResource {
	
	SubjectGroupSubjectService sgsService = new SubjectGroupSubjectService();

	@Secured
	@GET
	@Path("subjectgroup/{subjectGroupId}")
	public List<SubjectGroupSubject> getClassList(@PathParam("subjectGroupId") long subjectGroupId) {
		return sgsService.getSubjectGroupSubjects(subjectGroupId);
	}

	@Secured
	@POST
	public SubjectGroupSubject add(SubjectGroupSubject sgs) {
		return sgsService.add(sgs);
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		sgsService.delete(id);
	}

}
