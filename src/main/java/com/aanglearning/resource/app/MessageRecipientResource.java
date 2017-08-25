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
import com.aanglearning.model.app.MessageRecipient;
import com.aanglearning.service.app.MessageRecipientService;

@Path("/messagerecipient")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageRecipientResource {
	MessageRecipientService service = new MessageRecipientService();
	
	@Secured
	@POST
	public List<MessageRecipient> add(List<MessageRecipient> mrList) {
		return service.add(mrList);
	}
	
	@Secured
	@GET
	@Path("{groupId}/{groupMessageId}")
	public List<MessageRecipient> getMessageRecipients(@PathParam("groupId") long groupId,
			@PathParam("groupMessageId") long groupMessageId) {
		return service.getMessageRecipients(groupId, groupMessageId);
	}
	
	@Secured
	@GET
	@Path("{recipientId}")
	public List<MessageRecipient> getAllMessageRecipients(@PathParam("recipientId") long recipientId) {
		return service.getAllMessageRecipients(recipientId);
	}

}
