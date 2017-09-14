package com.aanglearning.resource.entity;

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
import com.aanglearning.model.entity.ClassEvent;
import com.aanglearning.service.entity.ClassEventService;

@Path("/classevent")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClassEventResource {
	
	ClassEventService service = new ClassEventService();

	@Secured
	@GET
	@Path("event/{eventId}")
	public List<ClassEvent> getClassList(@PathParam("eventId") long eventId) {
		return service.getEventClasses(eventId);
	}

	@Secured
	@POST
	public ClassEvent add(ClassEvent ce) {
		return service.add(ce);
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}
}
