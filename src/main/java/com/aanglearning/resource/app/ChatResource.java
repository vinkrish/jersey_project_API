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
import com.aanglearning.model.app.Chat;
import com.aanglearning.service.app.ChatService;

@Path("/chat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {
	ChatService service = new ChatService();
	
	@Secured
	@POST
	public Chat add(Chat chat) {
		return service.add(chat);
	}
	
	@Secured
	@GET
	@Path("teacher/{id}")
	public List<Chat> getChatParents(@PathParam("id") long id) {
		return service.getChatParents(id);
	}
	
	@Secured
	@GET
	@Path("parent/{id}")
	public List<Chat> getChatTeachers(@PathParam("id") long id) {
		return service.getChatTeachers(id);
	}
	
	@Secured
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

}
