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
import com.aanglearning.model.cce.CceCoscholastic;
import com.aanglearning.service.cce.CceCoscholasticService;

@Path("/ccecoscholastic")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceCoscholasticResource {
	
	CceCoscholasticService service = new CceCoscholasticService();

	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<CceCoscholastic> getCceCoscholastics(@PathParam("schoolId") long schoolId) {
		return service.getCceCoscholastics(schoolId);
	}

	@Secured
	@POST
	public CceCoscholastic add(CceCoscholastic CceCoSch) {
		return service.add(CceCoSch);
	}
	
	@Secured
	@PUT
	@Path("/{classId}")
	public void update(CceCoscholastic CceCoSch) {
		service.update(CceCoSch);
	}
	
	@Secured
	@DELETE
	@Path("/{classId}")
	public void delete(@PathParam("classId") long classId) {
		service.delete(classId);
	}

}
