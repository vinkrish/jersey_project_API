package com.aanglearning.resource.entity;

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
import com.aanglearning.model.entity.Homework;
import com.aanglearning.service.entity.HomeworkService;

@Path("/homework")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HomeworkResource {
	
	HomeworkService service = new HomeworkService();
	
	@Secured
	@GET
	@Path("section/{sectionId}/date/{homeworkDate}")
	public List<Homework> getHomeworkToday(@PathParam("sectionId") long sectionId,
			@PathParam("homeworkDate") String homeworkDate){
		return service.getUnHomeworkToday(sectionId, homeworkDate);
	}
	
	@Secured
	@POST
	@Path("delete")
	public void deleteHomework(List<Homework> homeworks) {
		service.deleteList(homeworks);
	}
	
	@Secured
	@POST
	public Homework add(Homework homework){
		return service.add(homework);
	}
	
	@Secured
	@PUT
	public void update(Homework homework){
		service.update(homework);
	}
	
	@Secured
	@DELETE
	@Path("{homeworkId}")
	public void delete(@PathParam("homeworkId") long homeworkId){
		service.delete(homeworkId);
	}

}
