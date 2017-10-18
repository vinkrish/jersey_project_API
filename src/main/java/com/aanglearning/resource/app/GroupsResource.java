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
	@Path("{groupId}")
	public Groups getGroup(@PathParam("groupId") long groupId) {
		return service.getGroup(groupId);
	}
	
	@Secured
	@GET
	@Path("stud/{studentId}/group/{id}")
	public List<Groups> getStudGroupsAboveId(@PathParam("studentId") long studentId,
			@PathParam("id") long id) {
		return service.getStudGroupsAboveId(studentId, id);
	}
	
	@Secured
	@GET
	@Path("stud/{studentId}")
	public List<Groups> getStudGroups(@PathParam("studentId") long studentId) {
		return service.getStudGroups(studentId);
	}
	
	@Secured
	@GET
	@Path("school/{schoolId}/group/{id}")
	public List<Groups> getSchoolGroupsAboveId(@PathParam("schoolId") long schoolId,
			@PathParam("id") long id) {
		return service.getSchoolGroupsAboveId(schoolId, id);
	}
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<Groups> getSchoolGroups(@PathParam("schoolId") long schoolId) {
		return service.getSchoolGroups(schoolId);
	}
	
	@Secured
	@GET
	@Path("student/{id}")
	public List<Groups> getStudentGroups(@PathParam("id") long id) {
		return service.getStudentGroups(id);
	}
	
	@Secured
	@GET
	@Path("teacher/{teacherId}/group/{id}")
	public List<Groups> getTeacherGroupsAboveId(@PathParam("teacherId") long teacherId,
			@PathParam("id") long id) {
		return service.getTeacherGroupsAboveId(teacherId, id);
	}
	
	@Secured
	@GET
	@Path("teacher/{id}")
	public List<Groups> getTeacherGroups(@PathParam("id") long id) {
		return service.getTeacherGroups(id);
	}
	
	@Secured
	@GET
	@Path("principal/teacher/{teacherId}/group/{id}")
	public List<Groups> getPrincipalGroupsAboveId(@PathParam("teacherId") long teacherId,
			@PathParam("id") long id) {
		return service.getPrincipalGroupsAboveId(teacherId, id);
	}
	
	@Secured
	@GET
	@Path("principal/teacher/{id}")
	public List<Groups> getPrincipalGroups(@PathParam("id") long id) {
		return service.getPrincipalGroups(id);
	}
	
	@Secured
	@GET
	@Path("principal/{schoolId}/group/{id}")
	public List<Groups> getAllGroupsAboveId(@PathParam("schoolId") long schoolId,
			@PathParam("id") long id) {
		return service.getAllGroupsAboveId(schoolId, id);
	}
	
	@Secured
	@GET
	@Path("principal/{schoolId}")
	public List<Groups> getAllGroups(@PathParam("schoolId") long schoolId) {
		return service.getAllGroups(schoolId);
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
	@Path("{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

	public Groups getGroupById(long groupId) {
		return service.getGroup(groupId);
	}
}
