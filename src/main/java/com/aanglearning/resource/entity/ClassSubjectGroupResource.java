package com.aanglearning.resource.entity;

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
import com.aanglearning.model.entity.ClassSubjectGroup;
import com.aanglearning.service.entity.ClassSubjectGroupService;

@Path("/classsubjectgroup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClassSubjectGroupResource {
	
	ClassSubjectGroupService csgService = new ClassSubjectGroupService();

	@Secured
	@GET
	@Path("class/{classId}")
	public List<ClassSubjectGroup> getClassList(@PathParam("classId") long classId) {
		return csgService.getClassSubjectGroups(classId);
	}

	@Secured
	@POST
	public ClassSubjectGroup add(ClassSubjectGroup csg) {
		return csgService.add(csg);
	}
	
	@Secured
	@PUT
	@Path("/{id}")
	public void update(ClassSubjectGroup csg) {
		csgService.update(csg);
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		csgService.delete(id);
	}
}
