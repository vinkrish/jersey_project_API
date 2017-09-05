package com.aanglearning.resource.entity;

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
import com.aanglearning.model.entity.Timetable;
import com.aanglearning.service.entity.TimetableService;

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
	@GET
	@Path("section/{sectionId}/day/{day}")
	public List<Timetable> getTimetable(@PathParam("sectionId") long sectionId, 
			@PathParam("day") String day) {
		return timetableService.getTimetableForDay(sectionId, day);
	}
	
	@Secured
	@POST
	public Timetable add(Timetable timetable) {
		return timetableService.add(timetable);
	}
	
	@Secured
	@POST
	@Path("day/{classId}/{sectionId}")
	public void addWeekDayTimetable(@PathParam("classId") long classId,
			@PathParam("sectionId") long sectionId) {
		timetableService.addWeekDayTimetable(classId, sectionId);
	}
	
	@Secured
	@POST
	@Path("week/{classId}/{sectionId}")
	public void copySectionTimetable(@PathParam("classId") long classId,
			@PathParam("sectionId") long sectionId) {
		timetableService.copySectionTimetable(classId, sectionId);
	}
	
	@Secured
	@POST
	@Path("saturday/{classId}/{sectionId}")
	public void addSaturdayTimetable(@PathParam("classId") long classId,
			@PathParam("sectionId") long sectionId) {
		timetableService.addSaturdayTimetable(classId, sectionId);
	}
	
	@Secured
	@PUT
	@Path("{timetableId}")
	public void update(Timetable timetable){
		timetableService.update(timetable);
	}
	
	@Secured
	@DELETE
	@Path("{timetableId}")
	public void delete(@PathParam("timetableId") long timetableId){
		timetableService.delete(timetableId);
	}
	
}
