package com.aanglearning.resource.app;

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
import com.aanglearning.model.app.Message;
import com.aanglearning.service.app.MessageService;

@Path("/message")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	MessageService service = new MessageService();
	
	@Secured
	@POST
	public Message add(Message message) {
		return service.add(message);
	}
	
	@Secured
	@GET
	@Path("{senderRole}/{senderId}/{recipientRole}/{recipientId}")
	public List<Message> getMessages(@PathParam("senderId") long senderId,
			@PathParam("senderRole") String senderRole,
			@PathParam("recipientId") long recipientId,
			@PathParam("recipientRole") String recipientRole) {
		return service.getMessages(senderId, senderRole, recipientId, recipientRole);
	}
	
	@Secured
	@GET
	@Path("group/{groupId}")
	public List<Message> getGroupMessages(@PathParam("groupId") long groupId) {
		return service.getGroupMessages(groupId);
	}
	
	@Secured
	@GET
	@Path("{senderRole}/{senderId}/{recipientRole}/{recipientId}/message/{messageId}")
	public List<Message> getMessagesFromId(@PathParam("senderId") long senderId,
			@PathParam("senderRole") String senderRole,
			@PathParam("recipientId") long recipientId,
			@PathParam("recipientRole") String recipientRole,
			@PathParam("messageId") long messageId) {
		return service.getMessagesFromId(senderId, senderRole, recipientId, recipientRole, messageId);
	}
	
	@Secured
	@GET
	@Path("group/{groupId}/message/{messageId}")
	public List<Message> getGroupMessagesFromId(@PathParam("groupId") long groupId,
			@PathParam("messageId") long messageId) {
		return service.getGroupMessagesFromId(groupId, messageId);
	}
	
	@Secured
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

}
