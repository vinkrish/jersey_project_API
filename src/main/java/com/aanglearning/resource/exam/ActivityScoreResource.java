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
import com.aanglearning.model.exam.ActivityScore;
import com.aanglearning.service.exam.ActivityScoreService;

@Path("/activityscore")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityScoreResource {
	
	ActivityScoreService scoreService = new ActivityScoreService();
	
	@Secured
	@GET
	@Path("activity/{activityId}")
	public List<ActivityScore> getActivityScores(@PathParam("activityId") long activityId) {
		return scoreService.getActivityScores(activityId);
	}
	
	@Secured
	@GET
	@Path("activity/{activityId}/student/{studentId}")
	public ActivityScore getActivityScore(@PathParam("activityId") long activityId,
			@PathParam("student") long studentId) {
		return scoreService.getActivityScore(activityId, studentId);
	}
	
	@Secured
	@POST
	public void add(List<ActivityScore> scores) {
		scoreService.add(scores);
	}
	
	@Secured
	@PUT
	public void update(List<ActivityScore> scores) {
		scoreService.update(scores);
	}
	
	@Secured
	@DELETE
	@Path("activity/{activityId}")
	public void delete(@PathParam("activityId") long activityId) {
		scoreService.delete(activityId);
	}

}
