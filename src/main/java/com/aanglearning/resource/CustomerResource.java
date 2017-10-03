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
import com.aanglearning.model.Customer;
import com.aanglearning.service.CustomerService;

@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
	
	CustomerService service = new CustomerService();
	
	@Secured
	@POST
	public Customer add(Customer customer) {
		return service.add(customer);
	}
	
	@Secured
	@GET
	@Path("client/{clientId}")
	public List<Customer> getCustomers(@PathParam("clientId") long clientId) {
		return service.getCustomers(clientId);
	}
	
	@Secured
	@GET
	@Path("client/{clientId}/id/{id}")
	public List<Customer> getCustomersFromId(@PathParam("clientId") long clientId,
			@PathParam("id") long id) {
		return service.getCustomersFromId(clientId, id);
	}
	
	@Secured
	@POST
	@Path("delete")
	public void deleteCustomers(List<Customer> customers) {
		service.deleteCustomers(customers);
	}

}
