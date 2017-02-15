package com.aanglearning.resource.cce;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.cce.CceCoschClass;
import com.aanglearning.service.cce.CceCoschClassService;

@Path("/ccecoscholasticclass")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CceCoschClassResourse {
	
	CceCoschClassService service = new CceCoschClassService();

	@Secured
	@GET
	@Path("coscholastic/{coscholasticId}")
	public List<CceCoschClass> getClassList(@PathParam("coscholasticId") long coscholasticId) {
		return service.getCceCoschClasses(coscholasticId);
	}

	@Secured
	@POST
	public CceCoschClass add(CceCoschClass ccc) {
		return service.add(ccc);
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

}
