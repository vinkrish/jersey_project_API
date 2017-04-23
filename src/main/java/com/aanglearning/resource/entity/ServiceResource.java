package com.aanglearning.resource.entity;

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
import com.aanglearning.model.entity.Service;
import com.aanglearning.service.entity.ServicesService;

@Path("/service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceResource {

	ServicesService service = new ServicesService();

	@Secured
	@GET
	@Path("school/{id}")
	public Service getService(@PathParam("id") long id) {
		return service.getService(id);
	}

	@Secured
	@POST
	public Service add(Service srvice) {
		return service.add(srvice);
	}

	@Secured
	@PUT
	public void update(Service srvice) {
		service.update(srvice);
	}

	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}
	
	public void addService(Service srvice) {
		service.add(srvice);
	}
}
