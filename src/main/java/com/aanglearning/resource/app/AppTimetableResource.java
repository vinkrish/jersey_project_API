package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.app.TeacherTimetable;
import com.aanglearning.model.entity.Timetable;
import com.aanglearning.service.app.AppTimetableService;

@Path("/app/timetable")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppTimetableResource {
	
AppTimetableService timetableService = new AppTimetableService();
	
	@Secured
	@GET
	@Path("section/{sectionId}")
	public List<Timetable> getSectionTimetable(@PathParam("sectionId") long sectionId) {
		return timetableService.getSectionTimetable(sectionId);
	}
	
	@Secured
	@GET
	@Path("teacher/{teacherId}")
	public List<TeacherTimetable> getTeacherTimetable(@PathParam("teacherId") long teacherId) {
		return timetableService.getTeacherTimetable(teacherId);
	}
}
