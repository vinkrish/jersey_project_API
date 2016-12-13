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

import com.project.guldu.model.CceTopicPrimary;
import com.project.guldu.service.CceTopicPrimaryService;

import authentication.Secured;

@Path("/ccetopicprimary")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceTopicPrimaryResource {
	
	CceTopicPrimaryService service = new CceTopicPrimaryService();

	@Secured
	@GET
	@Path("topicprimary/{topicPrimaryId}")
	public List<CceTopicPrimary> getCceSectionHeadings(@PathParam("topicPrimaryId") long topicPrimaryId) {
		return service.getCceTopicPrimarys(topicPrimaryId);
	}

	@Secured
	@POST
	public CceTopicPrimary add(CceTopicPrimary topicPrimary) {
		return service.add(topicPrimary);
	}
	
	@Secured
	@PUT
	@Path("/{topicPrimaryId}")
	public void update(CceTopicPrimary topicPrimary) {
		service.update(topicPrimary);
	}
	
	@Secured
	@DELETE
	@Path("/{topicPrimaryId}")
	public void delete(@PathParam("topicPrimaryId") long topicPrimaryId) {
		service.delete(topicPrimaryId);
	}
}
