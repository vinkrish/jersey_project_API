package com.aanglearning.resource.gallery;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.gallery.DeletedAlbum;
import com.aanglearning.service.gallery.DeletedAlbumService;

@Path("/deletedalbum")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeletedAlbumResource {
	
	DeletedAlbumService service = new DeletedAlbumService();
	
	@Secured
	@POST
	public DeletedAlbum add(DeletedAlbum deletedAlbum) {
		return service.add(deletedAlbum);
	}
	
	@Secured
	@POST
	@Path("new")
	public DeletedAlbum addNew(DeletedAlbum deletedAlbum) {
		return service.addNew(deletedAlbum);
	}
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<DeletedAlbum> getDeletedAlbums(@PathParam("schoolId") long schoolId) {
		return service.getDeletedAlbums(schoolId);
	}
	
	@Secured
	@GET
	@Path("{id}/school/{schoolId}")
	public List<DeletedAlbum> getDeletedAlbumsAboveId(@PathParam("schoolId") long schoolId,
			@PathParam("id") long id) {
		return service.getDeletedAlbumsAboveId(schoolId, id);
	}
	
	@Secured
	@GET
	@Path("all/{id}/school/{school}")
	public List<DeletedAlbum> getAllDelAlbAboveId(@PathParam("schoolId") long schoolId, 
			@PathParam("id") long id) {
		return service.getAllDelAlbAboveId(schoolId, id);
	}
	
	@Secured
	@GET
	@Path("all/school/{schoolId}")
	public List<DeletedAlbum> getAllDelAlb(@PathParam("schoolId") long schoolId) {
		return service.getAllDelAlb(schoolId);
	}
	
	@Secured
	@GET
	@Path("teacher/{schoolId}/{teacherId}/{id}")
	public List<DeletedAlbum> getTeacherDelAlbAboveId(@PathParam("schoolId") long schoolId,
			@PathParam("teacherId") long teacherId,
			@PathParam("id") long id) {
		return service.getTeacherDelAlbAboveId(schoolId, teacherId, id);
	}
	
	@Secured
	@GET
	@Path("teacher/{schoolId}/{teacherId}")
	public List<DeletedAlbum> getTeacherDelAlb(@PathParam("schoolId") long schoolId,
			@PathParam("teacherId") long teacherId) {
		return service.getTeacherDelAlb(schoolId, teacherId);
	}
	
	@Secured
	@GET
	@Path("student/{schoolId}/{classId}/{sectionId}/{id}")
	public List<DeletedAlbum> getStudDelAlbAboveId(@PathParam("schoolId") long schoolId,
			@PathParam("classId") long classId,
			@PathParam("sectionId") long sectionId,
			@PathParam("id") long id) {
		return service.getStudDelAlbAboveId(schoolId, classId, sectionId, id);
	}
	
	@Secured
	@GET
	@Path("student/{schoolId}/{classId}/{sectionId}")
	public List<DeletedAlbum> getStudDelAlb(@PathParam("schoolId") long schoolId,
			@PathParam("classId") long classId,
			@PathParam("sectionId") long sectionId) {
		return service.getStudDelAlb(schoolId, classId, sectionId);
	}

}
