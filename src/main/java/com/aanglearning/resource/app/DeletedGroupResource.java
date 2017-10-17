package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.app.DeletedGroup;
import com.aanglearning.service.app.DeletedGroupService;

@Path("/deletedgroup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeletedGroupResource {
	
	DeletedGroupService service = new DeletedGroupService();
	
	@Secured
	@POST
	public DeletedGroup add(DeletedGroup deletedGroup) {
		return service.add(deletedGroup);
	}
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<DeletedGroup> getDeletedGroups(@PathParam("schoolId") long schoolId) {
		return service.getDeletedGroups(schoolId);
	}
	
	@Secured
	@GET
	@Path("{id}/school/{schoolId}")
	public List<DeletedGroup> getDeletedGroupsAboveId(@PathParam("schoolId") long schoolId,
			@PathParam("id") long id) {
		return service.getDeletedGroupsAboveId(schoolId, id);
	}

}
