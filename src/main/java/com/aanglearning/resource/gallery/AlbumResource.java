package com.aanglearning.resource.gallery;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.gallery.Album;
import com.aanglearning.service.gallery.AlbumService;

@Path("/album")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlbumResource {
	
	AlbumService service = new AlbumService();
	
	@Secured
	@POST
	public Album add(Album album) {
		return service.add(album);
	}
	
	@Secured
	@GET
	@Path("{id}")
	public Album get(@PathParam("id") long id) {
		return service.getAlbum(id);
	}
	
	@Secured
	@GET
	@Path("{id}/school/{schoolId}")
	public List<Album> getAlbumsAboveId(@PathParam("schoolId") long schoolId, 
			@PathParam("id") long id) {
		return service.getAlbumsAboveId(schoolId, id);
	}
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<Album> getAlbums(@PathParam("schoolId") long schoolId) {
		return service.getAlbums(schoolId);
	}
	
	@Secured
	@GET
	@Path("all/{id}/school/{schoolId}")
	public List<Album> getAllAlbumsAboveId(@PathParam("schoolId") long schoolId, 
			@PathParam("id") long id) {
		return service.getAllAlbumsAboveId(schoolId, id);
	}
	
	@Secured
	@GET
	@Path("all/school/{schoolId}")
	public List<Album> getAllAlbums(@PathParam("schoolId") long schoolId) {
		return service.getAllAlbums(schoolId);
	}
	
	@Secured
	@PUT
	public void update(Album album) {
		service.updateAlbum(album);
	}
	
	@Secured
	@POST
	@Path("new")
	public Album addNew(Album album) {
		return service.addNew(album);
	}
	
	@Secured
	@GET
	@Path("teacher/{schoolId}/{teacherId}/{id}")
	public List<Album> getTeacherAlbumsAboveId(@PathParam("schoolId") long schoolId,
			@PathParam("teacherId") long teacherId,
			@PathParam("id") long id) {
		return service.getTeacherAlbumsAboveId(schoolId, teacherId, id);
	}
	
	@Secured
	@GET
	@Path("teacher/{schoolId}/{teacherId}")
	public List<Album> getTeacherAlbums(@PathParam("schoolId") long schoolId,
			@PathParam("teacherId") long teacherId) {
		return service.getTeacherAlbums(schoolId, teacherId);
	}
	
	@Secured
	@GET
	@Path("student/{schoolId}/{classId}/{sectionId}/{id}")
	public List<Album> getStudentAlbumsAboveId(@PathParam("schoolId") long schoolId,
			@PathParam("classId") long classId,
			@PathParam("sectionId") long sectionId,
			@PathParam("id") long id) {
		return service.getStudentAlbumsAboveId(schoolId, classId, sectionId, id);
	}
	
	@Secured
	@GET
	@Path("student/{schoolId}/{classId}/{sectionId}")
	public List<Album> getStudentAlbums(@PathParam("schoolId") long schoolId,
			@PathParam("classId") long classId,
			@PathParam("sectionId") long sectionId) {
		return service.getStudentAlbums(schoolId, classId, sectionId);
	}
	
}
