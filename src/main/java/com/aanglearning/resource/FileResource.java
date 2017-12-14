package com.aanglearning.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.aanglearning.service.FileService;

@Path("/excel")
public class FileResource {
	
	FileService service = new FileService();
	
	@GET
    @Path("{className}/{sectionName}/{sectionId}/att/{attendanceDate}")
    @Produces("application/vnd.ms-excel")
	public Response downloadAttendance(
			@PathParam("className") String className,
			@PathParam("sectionName") String sectionName,
			@PathParam("sectionId") long sectionId,
			@PathParam("attendanceDate") String attendanceDate) {
		return service.downloadAttendance(className, sectionName, sectionId, attendanceDate);
	}
	
	@GET
    @Path("{className}/{sectionName}/{sectionId}/hw/{homeworkDate}")
    @Produces("application/vnd.ms-excel")
	public Response downloadHomework(
			@PathParam("className") String className,
			@PathParam("sectionName") String sectionName,
			@PathParam("sectionId") long sectionId,
			@PathParam("homeworkDate") String homeworkDate) {
		return service.downloadHomework(className, sectionName, sectionId, homeworkDate);
	}

}
