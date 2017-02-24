package com.aanglearning.resource.parent;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.entity.Attendance;
import com.aanglearning.service.parent.AppAttendanceService;

@Path("/app/attendance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppAttendanceResource {

	AppAttendanceService service = new AppAttendanceService();

	@Secured
	@GET
	@Path("section/{sectionId}/student/{studentId}/date/{dateAttendance}")
	public List<Attendance> dailyAttendanceMarked(@PathParam("sectionId") long sectionId,
			@PathParam("studentId") long studentId, @PathParam("dateAttendance") String dateAttendance) {
		return service.getAttendanceMarked(sectionId, studentId, dateAttendance);
	}

}
