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
	public MessageRecipient add(MessageRecipient messageRecipient) {
		return service.add(messageRecipient);
	}
	
	@Secured
	@GET
	@Path("{id}")
	public List<MessageRecipient> getMessageRecipients(@PathParam("id") long id) {
		return service.getMessageRecipients(id);
	}

}
