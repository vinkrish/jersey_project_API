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

import com.project.guldu.model.ExamSubject;
import com.project.guldu.service.ExamSubjectService;

import authentication.Secured;

@Path("/examsubject")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamSubjectResource {

	ExamSubjectService examSubjectService = new ExamSubjectService();
	
	@Secured
	@GET
	@Path("exam/{examId}")
	public List<ExamSubject> getExamSubjects(@PathParam("examId") long examId) {
		return examSubjectService.getExamSubjects(examId);
	}
	
	@Secured
	@POST
	public ExamSubject add(ExamSubject exam) {
		return examSubjectService.add(exam);
	}
	
	@Secured
	@PUT
	@Path("/{examSubjectId}")
	public void update(ExamSubject exam) {
		examSubjectService.update(exam);
	}
	
	@Secured
	@DELETE
	@Path("/{examSubjectId}")
	public void delete(@PathParam("examSubjectId") long examSubjectId) {
		examSubjectService.delete(examSubjectId);
	}
	
}
