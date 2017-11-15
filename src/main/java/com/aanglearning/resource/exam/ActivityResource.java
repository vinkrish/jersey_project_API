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
import com.aanglearning.model.exam.Activity;
import com.aanglearning.service.exam.ActivityService;

@Path("/activity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
	
	ActivityService activityService = new ActivityService();
	
	@Secured
	@GET
	@Path("section/{sectionId}/exam/{examId}/subject/{subjectId}")
	public List<Activity> getActivities(@PathParam("sectionId") long sectionId,
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
