package com.aanglearning.resource.exam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.exam.SliptestPortion;
import com.aanglearning.service.exam.SliptestPortionService;

@Path("/sliptestportion")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SliptestPortionResource {
	
	SliptestPortionService spService = new SliptestPortionService();

	@Secured
	@GET
	@Path("sliptest/{sliptestId}")
	public List<SliptestPortion> getSliptestPortions(@PathParam("sliptestId") long sliptestId) {
		return spService.getSliptestPortions(sliptestId);
	}

	@Secured
	@POST
	public SliptestPortion add(SliptestPortion sgs) {
		return spService.add(sgs);
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		spService.delete(id);
	}
}
