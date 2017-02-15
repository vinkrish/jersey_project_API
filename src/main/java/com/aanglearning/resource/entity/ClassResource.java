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
import com.aanglearning.model.entity.Clas;
import com.aanglearning.service.entity.ClassService;

@Path("/class")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClassResource {

	ClassService classService = new ClassService();

	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<Clas> getClassList(@PathParam("schoolId") long schoolId) {
		return classService.getClassList(schoolId);
	}

	@Secured
	@POST
	public Clas add(Clas clas) {
		return classService.add(clas);
	}
	
	@Secured
	@PUT
	@Path("/{classId}")
	public void update(Clas clas) {
		classService.update(clas);
	}
	
	@Secured
	@DELETE
	@Path("/{classId}")
	public void delete(@PathParam("classId") long classId) {
		classService.delete(classId);
	}

}