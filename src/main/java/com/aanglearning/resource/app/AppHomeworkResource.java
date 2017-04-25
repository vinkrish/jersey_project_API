package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.entity.Homework;
import com.aanglearning.service.app.AppHomeworkService;

@Path("/app/homework")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppHomeworkResource {

	AppHomeworkService service = new AppHomeworkService();

	@Secured
	@GET
	@Path("section/{sectionId}")
	public List<Homework> getHomeworks(@PathParam("sectionId") long sectionId) {
		return service.getAllHomeworks(sectionId);
	}
	
	@Secured
	@GET
	@Path("section/{sectionId}/date/{lastDate}")
	public List<Homework> getHomeworks(@PathParam("sectionId") long sectionId,
			@PathParam("lastDate") String lastDate) {
		return service.getHomeworks(sectionId, lastDate);
	}
	
	@Secured
	@GET
	@Path("section/{sectionId}/homeworkDate/{homeworkDate}")
	public List<Homework> getTodaysHomeworks(@PathParam("sectionId") long sectionId,
			@PathParam("homeworkDate") String homeworkDate) {
		return service.getTodaysHomeworks(sectionId, homeworkDate);
	}

}
