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

import com.project.guldu.model.Homework;
import com.project.guldu.service.HomeworkService;

import authentication.Secured;

@Path("/homework")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HomeworkResource {
	
	HomeworkService homeworkService = new HomeworkService();
	
	@Secured
	@GET
	@Path("section/{sectionId}/date/{homeworkDate}")
	public List<Homework> getHomeworkToday(@PathParam("sectionId") long sectionId,
			@PathParam("homeworkDate") String homeworkDate){
		return homeworkService.getUnHomeworkToday(sectionId, homeworkDate);
	}
	
	@Secured
	@POST
	public Homework add(Homework homework){
		return homeworkService.add(homework);
	}
	
	@Secured
	@PUT
	@Path("{homeworkId}")
	public void update(Homework homework){
		homeworkService.update(homework);
	}
	
	@Secured
	@DELETE
	@Path("{homeworkId}")
	public void delete(@PathParam("homeworkId") long homeworkId){
		homeworkService.delete(homeworkId);
	}

}
