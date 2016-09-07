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
import com.project.guldu.model.SubActivity;
import com.project.guldu.service.SubActivityService;

import authentication.Secured;

@Path("/subactivity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubActivityResource {
	
	SubActivityService subActivityService = new SubActivityService();
	
	@Secured
	@GET
	@Path("activity/{activityId}")
	public List<SubActivity> getExamSubjects(@PathParam("activityId") long activityId) {
		return subActivityService.getSubActivities(activityId);
	}
	
	@Secured
	@POST
	public SubActivity add(SubActivity subactivity) {
		return subActivityService.add(subactivity);
	}
	
	@Secured
	@PUT
	public void update(SubActivity subactivity) {
		subActivityService.update(subactivity);
	}
	
	@Secured
	@DELETE
	@Path("/{subActivityId}")
	public void delete(@PathParam("subActivityId") long subActivityId) {
		subActivityService.delete(subActivityId);
	}

}
