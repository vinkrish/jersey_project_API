package com.aanglearning.resource.exam;

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

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.exam.Mark;
import com.aanglearning.service.exam.MarkService;

@Path("/mark")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarkResource {
	
	MarkService markService = new MarkService();
	
	@Secured
	@GET
	@Path("exam/{examId}/subject/{subjectId}/section/{sectionId}")
	public List<Mark> getMarks(@PathParam("examId") long examId,
			@PathParam("subjectId") long subjectId,
			@PathParam("sectionId") long sectionId) {
		return markService.getMarks(examId, subjectId, sectionId);
	}
	
	@Secured
	@GET
	@Path("exam/{examId}/subject/{subjectId}/section/{sectionId}/student/{studentId}")
	public Mark getMark(@PathParam("examId") long examId,
			@PathParam("subjectId") long subjectId,
			@PathParam("sectionId") long sectionId,
			@PathParam("studentId") long studentId) {
		return markService.getMark(examId, subjectId, sectionId, studentId);
	}
	
	@Secured
	@POST
	public void add(List<Mark> marks) {
		markService.add(marks);
	}
	
	@Secured
	@PUT
	public void update(List<Mark> marks) {
		markService.update(marks);
	}
	
	@Secured
	@DELETE
	@Path("exam/{examId}/subject/{subjectId}/section/{sectionId}")
	public void delete(@PathParam("examId") long examId,
			@PathParam("subjectId") long subjectId,
			@PathParam("sectionId") long sectionId) {
		markService.delete(examId, subjectId, sectionId);
	}

}
