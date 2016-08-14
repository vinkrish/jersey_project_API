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

import com.project.guldu.model.ExamSubjectGroup;
import com.project.guldu.service.ExamSubjectGroupService;

import authentication.Secured;

@Path("/examsubjectgroup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamSubjectGroupResource {
	
	ExamSubjectGroupService esgService = new ExamSubjectGroupService();
	
	@Secured
	@GET
	@Path("exam/{examId}")
	public List<ExamSubjectGroup> getExamSubjects(@PathParam("examId") long examId) {
		return esgService.getExamSubjectGroups(examId);
	}
	
	@Secured
	@POST
	public ExamSubjectGroup add(ExamSubjectGroup esg) {
		return esgService.add(esg);
	}
	
	@Secured
	@DELETE
	@Path("/{examSubjectGroupId}")
	public void delete(@PathParam("examSubjectGroupId") long examSubjectGroupId) {
		esgService.delete(examSubjectGroupId);
	}

}
