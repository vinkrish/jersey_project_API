package com.aanglearning.resource.exam;

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
import com.aanglearning.model.exam.GradeClassWise;
import com.aanglearning.service.exam.GradeClassWiseService;

@Path("/gradeclasswise")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GradeClassWiseResource {
	
	GradeClassWiseService gcwService = new GradeClassWiseService();
	
	@Secured
	@GET
	@Path("class/{classId}")
	public List<GradeClassWise> getExamSubjects(@PathParam("classId") long classId) {
		return gcwService.getGradesClassWise(classId);
	}
	
	@Secured
	@POST
	public GradeClassWise add(GradeClassWise gcw) {
		return gcwService.add(gcw);
	}
	
	@Secured
	@PUT
	public void update(GradeClassWise gcw) {
		gcwService.update(gcw);
	}
	
	@Secured
	@DELETE
	@Path("/{gcwId}")
	public void delete(@PathParam("gcwId") long gcwId) {
		gcwService.delete(gcwId);
	}

}
