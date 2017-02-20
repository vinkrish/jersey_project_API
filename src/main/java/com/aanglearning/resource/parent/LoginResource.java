package com.aanglearning.resource.parent;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aanglearning.model.Credentials;
import com.aanglearning.service.parent.LoginService;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
	
	LoginService service = new LoginService();
	
	@POST
	@Path("login")
	public Response parentLogin(Credentials credentials) {
		return service.authenticateUser(credentials);
	}

}
