package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.app.DeletedMessage;
import com.aanglearning.service.app.DeletedMessageService;

@Path("/deletedmessage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeletedMessageResource {
	
	DeletedMessageService service = new DeletedMessageService();
	
	@Secured
	@POST
	public DeletedMessage add(DeletedMessage message) {
		return service.add(message);
	}
	
	@Secured
	@GET
	@Path("group/{groupId}")
	public List<DeletedMessage> getGroupDeletedMessages(@PathParam("groupId") long groupId) {
		return service.getGroupDeletedMessages(groupId);
	}
	
	@Secured
	@GET
	@Path("{id}/group/{groupId}")
	public List<DeletedMessage> getGroupDeletedMessagesFromId(@PathParam("groupId") long groupId,
			@PathParam("id") long id) {
		return service.getGroupDeletedMessagesFromId(groupId, id);
	}

}
