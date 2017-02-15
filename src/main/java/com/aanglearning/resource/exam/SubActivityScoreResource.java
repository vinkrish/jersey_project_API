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
import com.aanglearning.model.exam.SubActivityScore;
import com.aanglearning.service.exam.SubActivityScoreService;

@Path("/subactivityscore")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubActivityScoreResource {
	
	SubActivityScoreService scoreService = new SubActivityScoreService();
	
	@Secured
	@GET
	@Path("subactivity/{subActivityId}")
	public List<SubActivityScore> getActivityScores(@PathParam("subActivityId") long subActivityId) {
		return scoreService.getSubActivityScores(subActivityId);
	}
	
	@Secured
	@POST
	public void add(List<SubActivityScore> scores) {
		scoreService.add(scores);
	}
	
	@Secured
	@PUT
	public void update(List<SubActivityScore> scores) {
		scoreService.update(scores);
	}
	
	@Secured
	@DELETE
	@Path("subactivity/{subActivityId}")
	public void delete(@PathParam("subActivityId") long subActivityId) {
		scoreService.delete(subActivityId);
	}

}
