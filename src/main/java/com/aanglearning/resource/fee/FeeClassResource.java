package com.aanglearning.resource.fee;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.fee.FeeClass;
import com.aanglearning.service.fee.FeeClassService;

@Path("/classfee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FeeClassResource {
	
	FeeClassService service = new FeeClassService();
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<FeeClass> getClassFees(@PathParam("schoolId") long schoolId) {
		return service.getClassFees(schoolId);
	}
	
	@Secured
	@PUT
	@Path("{classId}")
	public void update(FeeClass feeClass) {
		service.update(feeClass);
	}

}
