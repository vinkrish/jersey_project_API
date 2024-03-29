package com.aanglearning.resource.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aanglearning.model.Credentials;
import com.aanglearning.service.app.ParentService;

@Path("/parent")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParentResource {
	
	ParentService service = new ParentService();
	
	@POST
	@Path("login")
	public Response parentLogin(Credentials credentials) {
		return service.authenticateUser(credentials);
	}

}
