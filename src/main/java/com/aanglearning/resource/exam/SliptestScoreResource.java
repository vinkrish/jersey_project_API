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
import com.aanglearning.model.exam.SliptestScore;
import com.aanglearning.service.exam.SliptestScoreService;

@Path("/sliptestscore")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SliptestScoreResource {
	
	SliptestScoreService scoreService = new SliptestScoreService();
	
	@Secured
	@GET
	@Path("sliptest/{sliptestId}")
	public List<SliptestScore> getSliptestScores(@PathParam("sliptestId") long sliptestId) {
		return scoreService.getSliptestScores(sliptestId);
	}
	
	@Secured
	@POST
	public void add(List<SliptestScore> scores) {
		scoreService.add(scores);
	}
	
	@Secured
	@PUT
	public void update(List<SliptestScore> scores) {
		scoreService.update(scores);
	}
	
	@Secured
	@DELETE
	@Path("sliptest/{sliptestId}")
	public void delete(@PathParam("sliptestId") long sliptestId) {
		scoreService.delete(sliptestId);
	}

}
