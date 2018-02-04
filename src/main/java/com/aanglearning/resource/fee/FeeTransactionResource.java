package com.aanglearning.resource.fee;

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
import com.aanglearning.model.fee.FeeTransaction;
import com.aanglearning.service.fee.FeeTransactionService;

@Path("/studentfee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FeeTransactionResource {
	
	FeeTransactionService service = new FeeTransactionService();
	
	@Secured
	@GET
	@Path("student/{studentId}")
	public List<FeeTransaction> getStudentsFee(@PathParam("studentId") long studentId) {
		return service.getStudentFee(studentId);
	}

	@Secured
	@POST
	public FeeTransaction add(FeeTransaction feeTransaction) {
		return service.saveStudentFee(feeTransaction);
	}
	
	@Secured
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}

}
