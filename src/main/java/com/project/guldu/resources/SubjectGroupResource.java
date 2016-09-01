package com.project.guldu.resources;

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

import com.project.guldu.model.SubjectGroup;
import com.project.guldu.service.SubjectGroupService;

import authentication.Secured;

@Path("/subjectgroup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectGroupResource {
	
	SubjectGroupService subjectGroupService = new SubjectGroupService();
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<SubjectGroup> getSubjectGroupList(@PathParam("schoolId") long schoolId) {
		return subjectGroupService.getSubjectGroupList(schoolId);
	}
	
	@Secured
	@POST
	public SubjectGroup add(SubjectGroup subjectGroup) {
		return subjectGroupService.add(subjectGroup);
	}
	
	@Secured
	@PUT
	@Path("/{subjectGroupId}")
	public void update(SubjectGroup subjectGroup) {
		subjectGroupService.update(subjectGroup);
	}
	
	@Secured
	@DELETE
	@Path("/{subjectGroupId}")
	public void delete(@PathParam("subjectGroupId") long subjectGroupId) {
		subjectGroupService.delete(subjectGroupId);
	}

}
