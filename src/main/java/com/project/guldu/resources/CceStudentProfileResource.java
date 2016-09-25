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

import com.project.guldu.model.CceStudentProfile;
import com.project.guldu.service.CceStudentProfileService;
import authentication.Secured;

@Path("/ccestudentprofile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceStudentProfileResource {
	
	CceStudentProfileService cceStudProfService = new CceStudentProfileService();
	
	@Secured
	@GET
	@Path("section/{sectionId}/term/{term}")
	public List<CceStudentProfile> getCceStudentProfiles(@PathParam("sectionId") long sectionId,
			@PathParam("term") int term) {
		return cceStudProfService.getCceStudentProfiles(sectionId, term);
	}
	
	@Secured
	@POST
	public void add(List<CceStudentProfile> scores) {
		cceStudProfService.add(scores);
	}
	
	@Secured
	@PUT
	public void update(List<CceStudentProfile> scores) {
		cceStudProfService.update(scores);
	}
	
	@Secured
	@DELETE
	@Path("section/{sectionId}/term/{term}")
	public void delete(@PathParam("sectionId") long sectionId,
			@PathParam("term") int term) {
		cceStudProfService.delete(sectionId, term);
	}
}
