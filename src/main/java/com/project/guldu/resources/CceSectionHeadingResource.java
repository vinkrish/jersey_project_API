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

import com.project.guldu.model.CceSectionHeading;
import com.project.guldu.service.CceSectionHeadingService;

import authentication.Secured;

@Path("/ccesectionheading")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceSectionHeadingResource {
	
	CceSectionHeadingService service = new CceSectionHeadingService();

	@Secured
	@GET
	@Path("coscholastic/{coscholasticId}")
	public List<CceSectionHeading> getCceSectionHeadings(@PathParam("coscholasticId") long coscholasticId) {
		return service.getCceSectionHeadings(coscholasticId);
	}

	@Secured
	@POST
	public CceSectionHeading add(CceSectionHeading sectionHeading) {
		return service.add(sectionHeading);
	}
	
	@Secured
	@PUT
	@Path("/{coscholasticId}")
	public void update(CceSectionHeading sectionHeading) {
		service.update(sectionHeading);
	}
	
	@Secured
	@DELETE
	@Path("/{sectionHeadingId}")
	public void delete(@PathParam("sectionHeadingId") long sectionHeadingId) {
		service.delete(sectionHeadingId);
	}

}
