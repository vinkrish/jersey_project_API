package com.aanglearning.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.Credentials;
import com.aanglearning.service.AdminService;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {
	
	AdminService service = new AdminService();
	
	@Secured
	@POST
	@Path("elasticbeanstalk")
	public Response keepServerUp() {
		return service.keepServerUp();
	}
	
	@POST
	public Response authenticate(Credentials credentials) {
		return service.authenticateUser(credentials);
	}
	
	@POST
	@Path("admin")
	public Response teacherLogin(Credentials credentials) {
		return service.authenticateSuperAdmin(credentials);
	}
	
}
