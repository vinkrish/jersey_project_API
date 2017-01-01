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

import com.project.guldu.model.CceAspectPrimary;
import com.project.guldu.service.CceAspectPrimaryService;

import authentication.Secured;

@Path("/cceaspectprimary")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceAspectPrimaryResource {
	
	CceAspectPrimaryService service = new CceAspectPrimaryService();

	@Secured
	@GET
	@Path("topicprimary/{topicId}")
	public List<CceAspectPrimary> getCceAspectPrimarys(@PathParam("topicId") long topicId) {
		return service.getCceAspectPrimarys(topicId);
	}

	@Secured
	@POST
	public CceAspectPrimary add(CceAspectPrimary aspectPrimary) {
		return service.add(aspectPrimary);
	}
	
	@Secured
	@PUT
	@Path("/{aspectPrimaryId}")
	public void update(CceAspectPrimary aspectPrimary) {
		service.update(aspectPrimary);
	}
	
	@Secured
	@DELETE
	@Path("/{aspectId}")
	public void delete(@PathParam("aspectId") long aspectId) {
		service.delete(aspectId);
	}

}
