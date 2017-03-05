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

	AttendanceService service = new AttendanceService();
	
	@Secured
	@GET
	@Path("daily/marked/section/{sectionId}/date/{dateAttendance}")
	public List<Attendance> dailyAttendanceMarked(@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return service.dailyAttendanceMarked(sectionId, dateAttendance);
	}
	
	@Secured
	@GET
	@Path("daily/unmarked/section/{sectionId}/date/{dateAttendance}")
	public List<Attendance> dailyAttendanceUnMarked(@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return service.dailyAttendanceUnmarked(sectionId, dateAttendance);
	}
	
	@Secured
	@GET
	@Path("session/marked/{session}/{sectionId}/{dateAttendance}")
	public List<Attendance> sessionAttendanceMarked(@PathParam("session") int session, 
			@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return service.sessionAttendanceMarked(session, sectionId, dateAttendance);
	}
	
	@Secured
	@GET
	@Path("session/unmarked/{session}/{sectionId}/{dateAttendance}")
	public List<Attendance> sessionAttendanceUnmarked(@PathParam("session") int session, 
			@PathParam("sectionId") long sectionId,
			@PathParam("dateAttendance") String dateAttendance) {
		return service.sessionAttendanceUnmarked(session, sectionId, dateAttendance);
	}
	
	@Secured
	@POST
	@Path("noAbsentees")
	public Attendance noAbsentees(Attendance attendance){
		return service.noAbsentees(attendance);
	}
	
	@Secured
	@POST
	@Path("list")
	public void add(List<Attendance> attendances){
		service.addAttendanceList(attendances);
	}
	
	@Secured
	@PUT
	@Path("{attendanceId}")
	public void update(Attendance attendance){
		service.update(attendance);
	}
	
	@Secured
	@DELETE
	@Path("{attendanceId}")
	public void delete(@PathParam("attendanceId") long attendanceId){
		service.delete(attendanceId);
	}
	
	@Secured
	@DELETE
	@Path("deleteWhole")
	public void deleteWhole(Attendance attendance){
		service.deleteWhole(attendance);
	}

}
