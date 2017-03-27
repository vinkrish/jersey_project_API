package com.aanglearning.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.entity.Clas;
import com.aanglearning.service.SharedService;

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
