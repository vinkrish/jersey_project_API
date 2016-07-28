package com.project.guldu.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.guldu.model.Homework;
import com.project.guldu.service.HomeworkService;

@Path("/homework")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class HomeworkResource {
	
	HomeworkService homeworkService = new HomeworkService();
	
	@GET
	@Path("school/{schoolId}/date/{homeworkDate}")
	public List<Homework> getHomeworkToday(@PathParam("schoolId") long schoolId,
			@PathParam("homeworkDate") String homeworkDate){
		return homeworkService.getHomeworkToday(schoolId, homeworkDate);
	}
	
	@GET
	@Path("getallhomework/{homeworkIndex}")
	public List<Homework> getHomeworkRange(@PathParam("homeworkIndex") long homeworkIndex){
		return homeworkService.getHomewokRange(homeworkIndex);
	}
	
	@POST
	public void addHomework(String homeworkStr){
		homeworkService.addHomework(homeworkStr);
	}

}
