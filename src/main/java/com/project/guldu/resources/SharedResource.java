package com.project.guldu.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.project.guldu.model.Clas;
import com.project.guldu.service.SharedService;

import authentication.Secured;

@Path("/shared")
@Consumes(MediaType.APPLICATION_JSON)
public class SharedResource {
	
	SharedService sharedService = new SharedService();
	
	@Secured
	@POST
	@Path("subjectteacher")
	public void add(Clas clas) {
		sharedService.insertSubjectTeacher(clas);
	}
	
}
