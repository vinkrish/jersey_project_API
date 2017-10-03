package com.aanglearning.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.Client;
import com.aanglearning.model.SmsCustomer;
import com.aanglearning.service.SmsCustomerService;

@Path("/smscustomer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SmsCustomerResource {
	
	SmsCustomerService service = new SmsCustomerService();
	
	@Secured
	@GET
	@Path("client/{clientId}")
	public List<SmsCustomer> getSms(@PathParam("clientId") long clientId) {
		return service.getSms(clientId);
	}
	
	@Secured
	@GET
	@Path("client/{clientId}/message/{messageId}")
	public List<SmsCustomer> getSmsFromId(@PathParam("clientId") long clientId,
			@PathParam("messageId") long messageId) {
		return service.getSmsFromId(clientId, messageId);
	}
	
	@Secured
	@POST
	public Client smsAllCustomer(SmsCustomer smsCustomer) {
		return service.smsAllCustomer(smsCustomer);
	}
	
	@Secured
	@POST
	@Path("multiple")
	public Client smsMultipleCustomer(SmsCustomer smsCustomer) {
		return service.smsMultipleCustomer(smsCustomer);
	}

}
