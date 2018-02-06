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
import com.aanglearning.model.entity.Student;
import com.aanglearning.service.fee.FeeStudentService;

@Path("/sectionfee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FeeStudentResource {
	
	FeeStudentService service = new FeeStudentService();
	
	@Secured
	@GET
	@Path("section/{sectionId}")
	public List<Student> getStudentsFee(@PathParam("sectionId") long sectionId) {
		return service.getStudentsFee(sectionId);
	}
	
	@Secured
	@PUT
	@Path("{studentId}")
	public void update(Student feeStudent) {
		service.update(feeStudent);
	}

}
