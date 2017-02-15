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
import com.aanglearning.model.entity.Portion;
import com.aanglearning.service.entity.PortionService;

@Path("/portion")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PortionResource {
	
	PortionService portionService = new PortionService();

	@Secured
	@GET
	@Path("class/{classId}/subject/{subjectId}")
	public List<Portion> getSectionList(@PathParam("classId") long classId, @PathParam("subjectId") long subjectId) {
		return portionService.getPortions(classId, subjectId);
	}

	@Secured
	@POST
	public Portion add(Portion portion) {
		return portionService.add(portion);
	}
	
	@Secured
	@PUT
	@Path("/{portionId}")
	public void update(Portion portion) {
		portionService.update(portion);
	}
	
	@Secured
	@DELETE
	@Path("/{portionId}")
	public void delete(@PathParam("portionId") long portionId) {
		portionService.delete(portionId);
	}

}
