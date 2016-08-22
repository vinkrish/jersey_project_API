package com.project.guldu.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.guldu.model.Credentials;
import com.project.guldu.service.AdminService;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {
	
	AdminService adminService = new AdminService();
	
	@POST
	@Produces("application/json")
	public Response authenticate(Credentials credentials) {
		return adminService.authenticateUser(credentials);
	}
	
}
