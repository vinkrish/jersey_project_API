package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.app.Groups;
import com.aanglearning.service.app.GroupsService;

@Path("/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupsResource {
	
	GroupsService service = new GroupsService();
	
	@Secured
	@GET
	@Path("student/{id}")
	public List<Groups> getStudentGroups(@PathParam("id") long id) {
		return service.getStudentGroups(id);
	}
	
	@Secured
	@GET
	@Path("teacher/{id}")
	public List<Groups> getTeacherGroups(@PathParam("id") long id) {
		return service.getTeacherGroups(id);
	}
	
	@Secured
	@POST
	public Groups add(Groups group) {
		return service.add(group);
	}
	
	@Secured
	@PUT
	public void update(Groups group) {
		service.update(group);
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

	public Groups getGroupById(long groupId) {
		return service.getGroup(groupId);
	}
}
