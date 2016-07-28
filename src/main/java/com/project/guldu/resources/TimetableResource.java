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

@Path("/timetable")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TimetableResource {
	
	TimetableService timetableService = new TimetableService();
	
	@GET
	@Path("section/{sectionId}")
	public List<Timetable> getTimetable(@PathParam("sectionId") long sectionId) {
		return timetableService.getTimetable(sectionId);
	}
	
	@POST
	public void addTimetable(String timetableStr) {
		timetableService.addTimetable(timetableStr);
	}

}
