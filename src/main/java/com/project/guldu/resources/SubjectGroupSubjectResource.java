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

import com.project.guldu.model.SubjectGroupSubject;
import com.project.guldu.service.SubjectGroupSubjectService;

@Path("/subjectgroupsubject")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectGroupSubjectResource {
	
	SubjectGroupSubjectService sgsService = new SubjectGroupSubjectService();

	@GET
	@Path("subjectgroup/{subjectGroupId}")
	public List<SubjectGroupSubject> getClassList(@PathParam("subjectGroupId") long subjectGroupId) {
		return sgsService.getSubjectGroupSubjects(subjectGroupId);
	}

	@POST
	public SubjectGroupSubject add(SubjectGroupSubject sgs) {
		return sgsService.add(sgs);
	}
	
	@PUT
	@Path("/{id}")
	public void update(SubjectGroupSubject sgs) {
		sgsService.update(sgs);
	}
	
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("classId") long id) {
		sgsService.delete(id);
	}

}
