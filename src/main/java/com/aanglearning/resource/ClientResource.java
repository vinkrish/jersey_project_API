package com.aanglearning.resource;

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
import com.aanglearning.model.Client;
import com.aanglearning.service.ClientService;

@Path("/client")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {
	
	ClientService service = new ClientService();
	
	@Secured
	@POST
	public Client add(Client client) {
		return service.add(client);
	}
	
	@Secured
	@GET
	public List<Client> getClients() {
		return service.getClients();
	}
	
	@Secured
	@GET
	@Path("{id}")
	public Client getClient(@PathParam("id") long id) {
		return service.getClient(id);
	}
	
	@Secured
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

}
