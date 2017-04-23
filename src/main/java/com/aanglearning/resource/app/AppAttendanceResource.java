package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.app.AttendanceSet;
import com.aanglearning.model.entity.Attendance;
import com.aanglearning.service.app.AppAttendanceService;

@Path("/app/attendance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppAttendanceResource {

	AppAttendanceService service = new AppAttendanceService();
	
	@Secured
	@GET
	@Path("section/{sectionId}")
	public List<Attendance> dailyAttendance(@PathParam("sectionId") long sectionId) {
		return service.getAttendance(sectionId);
	}

	@Secured
	@GET
	@Path("section/{sectionId}/date/{lastDate}")
	public List<Attendance> dailyAttendance(@PathParam("sectionId") long sectionId,
			@PathParam("lastDate") String lastDate) {
		return service.getAttendance(sectionId, lastDate);
	}
	
	@Secured
	@GET
	@Path("section/{sectionId}/currentDate/{dateAttendance}")
	public List<Attendance> dailyAttendanceMarked(@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return service.getTodaysAttendance(sectionId, dateAttendance);
	}
	
	@Secured
	@GET
	@Path("section/{sectionId}/date/{dateAttendance}/session/{session}")
	public AttendanceSet getAttendanceSet(@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance,
			@PathParam("session") int session) {
		return service.getAttendanceSet(sectionId, dateAttendance, session);
	}
	
	@Secured
	@POST
	public void saveAttendance(List<Attendance> attendanceList) {
		service.saveAttendance(attendanceList);
	}
	
	@Secured
	@POST
	@Path("delete")
	public void deleteAttendance(List<Attendance> attendanceList) {
		service.deleteAttendance(attendanceList);
	}

}
