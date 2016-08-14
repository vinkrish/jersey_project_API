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

import com.project.guldu.model.Exam;
import com.project.guldu.service.ExamService;

import authentication.Secured;

@Path("/exam")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamResource {
	
	ExamService examService = new ExamService();
	
	@Secured
	@GET
	@Path("class/{classId}")
	public List<Exam> getExamList(@PathParam("classId") long classId) {
		return examService.getExamList(classId);
	}
	
	@Secured
	@POST
	public Exam add(Exam exam) {
		return examService.add(exam);
	}
	
	@Secured
	@PUT
	@Path("/{examId}")
	public void update(Exam exam) {
		examService.update(exam);
	}
	
	@Secured
	@DELETE
	@Path("/{examId}")
	public void delete(@PathParam("examId") long examId) {
		examService.delete(examId);
	}

}
