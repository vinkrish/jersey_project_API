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

import com.project.guldu.model.Clas;
import com.project.guldu.service.ClassService;

import authentication.Secured;

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

	@POST
	@Path("/list")
	public void addClasses(String classStr) {
		classService.addClasses(classStr);
	}
	
	@POST
	public Clas add(Clas clas) {
		return classService.add(clas);
	}
	
	@PUT
	@Path("/{classId}")
	public void update(Clas clas) {
		classService.update(clas);
	}
	
	@DELETE
	@Path("/{classId}")
	public void delete(@PathParam("classId") long classId) {
		classService.delete(classId);
	}

}
