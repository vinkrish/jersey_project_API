package com.aanglearning.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.Authorization;
import com.aanglearning.service.AuthorizationService;

@Path("/authorization")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationResource {
	
	AuthorizationService service = new AuthorizationService();
	
	@Secured
	@POST
	@Path("fcm")
	public void update(Authorization authorization) {
		service.updateFcmToken(authorization);
	}
}
