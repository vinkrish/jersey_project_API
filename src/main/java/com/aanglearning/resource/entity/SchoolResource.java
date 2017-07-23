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
import com.aanglearning.model.entity.School;
import com.aanglearning.service.entity.SchoolService;

@Path("/school")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SchoolResource {

	SchoolService service = new SchoolService();

	@GET
	@Path("/{id}")
	public School getSchoolById(@PathParam("id") long id) {
		return service.getSchoolById(id);
	}
	
	@GET
	@Path("admin/{adminPassword}")
	public List<School> getSchools(@PathParam("adminPassword") String adminPassword) {
		return service.getSchools(adminPassword);
	}
	
	@Secured
	@GET
	public List<School> getSchoolList() {
		return service.getSchoolList();
	}
	
	public School getSchoolByUsername(String username) {
		return service.getSchoolByUserName(username);
	}

	@POST
	public School add(School school) {
		return service.add(school);
	}
	
	@Secured
	@PUT
	@Path("/{schoolId}")
	public void update(School school) {
		service.update(school);
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

}
