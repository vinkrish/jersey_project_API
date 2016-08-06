package com.project.guldu.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.guldu.model.Timetable;
import com.project.guldu.service.TimetableService;

import authentication.Secured;

@Path("/timetable")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TimetableResource {
	
	TimetableService timetableService = new TimetableService();
	
	@Secured
	@GET
	@Path("section/{sectionId}")
	public List<Timetable> getTimetable(@PathParam("sectionId") long sectionId) {
		return timetableService.getTimetable(sectionId);
	}
	
	@Secured
	@POST
	public void addTimetable(String timetableStr) {
		timetableService.addTimetable(timetableStr);
	}

}
