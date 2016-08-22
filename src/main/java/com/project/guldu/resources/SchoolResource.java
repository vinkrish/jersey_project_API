package com.project.guldu.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.guldu.model.School;
import com.project.guldu.service.SchoolService;

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
