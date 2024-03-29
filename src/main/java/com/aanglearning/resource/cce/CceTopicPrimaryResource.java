package com.aanglearning.resource.cce;

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
import com.aanglearning.model.cce.CceTopicPrimary;
import com.aanglearning.service.cce.CceTopicPrimaryService;

@Path("/ccetopicprimary")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceTopicPrimaryResource {
	
	CceTopicPrimaryService service = new CceTopicPrimaryService();

	@Secured
	@GET
	@Path("sectionheading/{sectionHeadingId}")
	public List<CceTopicPrimary> getCceTopicPrimarys(@PathParam("sectionHeadingId") long sectionHeadingId) {
		return service.getCceTopicPrimarys(sectionHeadingId);
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
	@Path("/{topicId}")
	public void delete(@PathParam("topicId") long topicId) {
		service.delete(topicId);
	}
}
