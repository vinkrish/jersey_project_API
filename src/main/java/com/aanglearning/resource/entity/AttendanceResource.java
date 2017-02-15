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
import com.aanglearning.model.entity.Attendance;
import com.aanglearning.service.entity.AttendanceService;

@Path("/attendance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AttendanceResource {

	AttendanceService attendanceService = new AttendanceService();
	
	@Secured
	@GET
	@Path("daily/marked/section/{sectionId}/date/{dateAttendance}")
	public List<Attendance> dailyAttendanceMarked(@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return attendanceService.dailyAttendanceMarked(sectionId, dateAttendance);
	}
	
	@Secured
	@GET
	@Path("daily/unmarked/section/{sectionId}/date/{dateAttendance}")
	public List<Attendance> dailyAttendanceUnMarked(@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return attendanceService.dailyAttendanceUnmarked(sectionId, dateAttendance);
	}
	
	@Secured
	@GET
	@Path("session/marked/{session}/{sectionId}/{dateAttendance}")
	public List<Attendance> sessionAttendanceMarked(@PathParam("session") int session, 
			@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return attendanceService.sessionAttendanceMarked(session, sectionId, dateAttendance);
	}
	
	@Secured
	@GET
	@Path("session/unmarked/{session}/{sectionId}/{dateAttendance}")
	public List<Attendance> sessionAttendanceUnmarked(@PathParam("session") int session, 
			@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return attendanceService.sessionAttendanceUnmarked(session, sectionId, dateAttendance);
	}

	@Secured
	@POST
	public Attendance add(Attendance attendance){
		return attendanceService.add(attendance);
	}
	
	@Secured
	@POST
	@Path("list")
	public void add(List<Attendance> attendances){
		attendanceService.addAttendanceList(attendances);
	}
	
	@Secured
	@PUT
	@Path("{attendanceId}")
	public void update(Attendance attendance){
		attendanceService.update(attendance);
	}
	
	@Secured
	@DELETE
	@Path("{attendanceId}")
	public void delete(@PathParam("attendanceId") long attendanceId){
		attendanceService.delete(attendanceId);
	}

}