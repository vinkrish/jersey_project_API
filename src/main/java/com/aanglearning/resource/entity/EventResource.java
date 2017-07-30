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
import com.aanglearning.model.entity.Event;
import com.aanglearning.service.entity.EventService;

@Path("/event")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
	
	EventService service = new EventService();
	
	@Secured
	@GET
	@Path("school/{id}")
	public List<Event> getSchoolEvents(@PathParam("id") long id) {
		return service.getSchoolEvents(id);
	}
	
	@Secured
	@POST
	public Event add(Event event) {
		return service.add(event);
	}
	
	@Secured
	@PUT
	public void update(Event event) {
		service.update(event);
	}
	
	@Secured
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") int id) {
		service.delete(id);
	}

}
