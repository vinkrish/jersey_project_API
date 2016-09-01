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

import com.project.guldu.model.Teacher;
import com.project.guldu.service.TeacherService;

import authentication.Secured;

@Path("/teacher")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherResource {
	
	TeacherService teacherService = new TeacherService();
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<Teacher> getTeacherList(@PathParam("schoolId") long schoolId){
		return teacherService.getTeacherList(schoolId);
	}
	
	@Secured
	@POST
	public Teacher add(Teacher teacher) {
		return teacherService.add(teacher);
	}
	
	@Secured
	@PUT
	@Path("/{teacherId}")
	public void update(Teacher teacher) {
		teacherService.update(teacher);
	}
	
	@Secured
	@DELETE
	@Path("/{teacherId}")
	public void delete(@PathParam("teacherId") long teacherId) {
		teacherService.delete(teacherId);
	}

}
