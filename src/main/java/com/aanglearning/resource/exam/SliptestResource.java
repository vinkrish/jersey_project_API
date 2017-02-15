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
import com.aanglearning.model.exam.Sliptest;
import com.aanglearning.service.exam.SliptestService;

@Path("/sliptest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SliptestResource {
	
	SliptestService sliptestService = new SliptestService();

	@Secured
	@GET
	@Path("section/{sectionId}/subject/{subjectId}")
	public List<Sliptest> getSectionList(@PathParam("sectionId") long sectionId, @PathParam("subjectId") long subjectId) {
		return sliptestService.getSliptests(sectionId, subjectId);
	}

	@Secured
	@POST
	public Sliptest add(Sliptest sliptest) {
		return sliptestService.add(sliptest);
	}
	
	@Secured
	@PUT
	@Path("/{sliptestId}")
	public void update(Sliptest sliptest) {
		sliptestService.update(sliptest);
	}
	
	@Secured
	@DELETE
	@Path("/{sliptestId}")
	public void delete(@PathParam("sliptestId") long sliptestId) {
		sliptestService.delete(sliptestId);
	}

}
