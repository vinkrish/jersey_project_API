package com.aanglearning.resource.entity;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.model.entity.School;
import com.aanglearning.service.entity.SchoolService;

@Path("/school")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SchoolResource {

	SchoolService schoolService = new SchoolService();

	@GET
	@Path("/{schoolId}")
	public School getSchoolById(@PathParam("schoolId") long schoolId) {
		return schoolService.getSchoolById(schoolId);
	}
	
	public School getSchoolByUsername(String username) {
		return schoolService.getSchoolByUserName(username);
	}

	@POST
	public void addSchool(String schoolStr) {
		schoolService.addSchool(schoolStr);
	}

}
