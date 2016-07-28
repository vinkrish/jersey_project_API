package com.project.guldu.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.guldu.model.Attendance;
import com.project.guldu.service.AttendanceService;

@Path("/attendance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AttendanceResource {

	AttendanceService attendanceService = new AttendanceService();

	@GET
	@Path("school/{schoolId}/date/{dateAttendance}")
	public List<Attendance> getAttendanceToday(@PathParam("schoolId") long schoolId,
			@PathParam("dateAttendance") String dateAttendance) {
		return attendanceService.getAttendanceToday(schoolId, dateAttendance);
	}

	@GET
	@Path("getallattendance/{attendanceIndex}")
	public List<Attendance> getAttendanceRange(@PathParam("attendanceIndex") long attendanceIndex) {
		return attendanceService.getAttendanceRange(attendanceIndex);
	}

	@POST
	public void addAttendance(String attendanceStr) {
		attendanceService.addAttendance(attendanceStr);
	}

}
