package com.aanglearning.resource.cce;

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
import com.aanglearning.model.cce.CceAspectGrade;
import com.aanglearning.service.cce.AspectGradeService;

@Path("/cceaspectgrade")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceAspectGradeResource {
	
AspectGradeService service = new AspectGradeService();
	
	@Secured
	@GET
	@Path("aspect/{aspectId}/section/{sectionId}/term/{term}")
	public List<CceAspectGrade> getGrades(@PathParam("aspectId") long aspectId,
			@PathParam("sectionId") long sectionId,
			@PathParam("term") int term) {
		return service.getGrades(aspectId, sectionId, term);
	}
	
	@Secured
	@POST
	public void add(List<CceAspectGrade> grades) {
		service.add(grades);
	}
	
	@Secured
	@PUT
	public void update(List<CceAspectGrade> grades) {
		service.update(grades);
	}
	
	@Secured
	@DELETE
	@Path("aspect/{aspectId}/section/{sectionId}/term/{term}")
	public void delete(@PathParam("aspectId") long aspectId,
			@PathParam("sectionId") long sectionId,
			@PathParam("term") int term) {
		service.delete(aspectId, sectionId, term);
	}

}
