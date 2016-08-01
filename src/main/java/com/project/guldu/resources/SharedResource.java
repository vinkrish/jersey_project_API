package com.project.guldu.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.project.guldu.service.SharedService;

@Path("/shared")
@Consumes(MediaType.TEXT_PLAIN)
public class SharedResource {
	
	SharedService sharedService = new SharedService();
	
	@POST
	@Path("subjectteacher/{classId}")
	public void add(@PathParam("classId") long classId) {
		sharedService.insertSubjectTeacher(classId);
	}
	
}
