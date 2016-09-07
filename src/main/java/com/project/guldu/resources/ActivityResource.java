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

import com.project.guldu.model.Activity;
import com.project.guldu.service.ActivityService;

import authentication.Secured;

@Path("/activity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
	
	ActivityService activityService = new ActivityService();
	
	@Secured
	@GET
	@Path("section/{sectionId}/exam/{examId}/subject/{subjectId}")
	public List<Activity> getExamSubjects(@PathParam("sectionId") long sectionId,
			@PathParam("examId") long examId,
			@PathParam("subjectId") long subjectId) {
		return activityService.getActivities(sectionId, examId, subjectId);
	}
	
	@Secured
	@POST
	public Activity add(Activity activity) {
		return activityService.add(activity);
	}
	
	@Secured
	@PUT
	public void update(Activity activity) {
		activityService.update(activity);
	}
	
	@Secured
	@DELETE
	@Path("/{activityId}")
	public void delete(@PathParam("activityId") long activityId) {
		activityService.delete(activityId);
	}

}
