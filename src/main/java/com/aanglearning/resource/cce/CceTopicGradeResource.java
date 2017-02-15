package com.aanglearning.resource.cce;

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
import com.aanglearning.model.cce.CceTopicGrade;
import com.aanglearning.service.cce.CceTopicGradeService;

@Path("/ccetopicgrade")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceTopicGradeResource {

	CceTopicGradeService service = new CceTopicGradeService();

	@Secured
	@GET
	@Path("topicprimary/{topicId}")
	public List<CceTopicGrade> getCceTopicGrades(@PathParam("topicId") long topicId) {
		return service.getCceTopicGrades(topicId);
	}

	@Secured
	@POST
	public CceTopicGrade add(CceTopicGrade topicGrade) {
		return service.add(topicGrade);
	}
	
	@Secured
	@PUT
	@Path("/{aspectPrimaryId}")
	public void update(CceTopicGrade topicGrade) {
		service.update(topicGrade);
	}
	
	@Secured
	@DELETE
	@Path("/{topicGradeId}")
	public void delete(@PathParam("topicGradeId") long topicGradeId) {
		service.delete(topicGradeId);
	}
	
}
