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
import com.aanglearning.model.app.UserGroup;
import com.aanglearning.service.app.UserGroupService;

@Path("/usergroup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserGroupResource {

	UserGroupService service = new UserGroupService();

	@Secured
	@GET
	@Path("groups/{groupId}")
	public List<UserGroup> getUserGroups(@PathParam("groupId") long groupId) {
		return service.getUserGroups(groupId);
	}

	@Secured
	@POST
	public void add(List<UserGroup> userGroups) {
		service.add(userGroups);
	}

	@Secured
	@DELETE
	@Path("groups/{groupId}")
	public void delete(@PathParam("groupId") long groupId) {
		service.delete(groupId);
	}

	@Secured
	@DELETE
	@Path("{id}")
	public void deleteUser(@PathParam("id") long id) {
		service.deleteUser(id);
	}
	
	public void add(UserGroup userGroup) {
		service.add(userGroup);
	}

}
