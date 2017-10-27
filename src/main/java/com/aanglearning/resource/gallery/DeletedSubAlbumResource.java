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
import com.aanglearning.model.gallery.DeletedSubAlbum;
import com.aanglearning.service.gallery.DeletedSubAlbumService;

@Path("/deletedsubalbum")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeletedSubAlbumResource {
	
	DeletedSubAlbumService service = new DeletedSubAlbumService();
	
	@Secured
	@POST
	public DeletedSubAlbum add(DeletedSubAlbum deletedSubAlbum) {
		return service.add(deletedSubAlbum);
	}
	
	@Secured
	@GET
	@Path("album/{albumId}")
	public List<DeletedSubAlbum> getDeletedSubAlbums(@PathParam("albumId") long albumId) {
		return service.getDeletedSubAlbums(albumId);
	}
	
	@Secured
	@GET
	@Path("{id}/album/{albumId}")
	public List<DeletedSubAlbum> getDeletedAlbumsAboveId(@PathParam("albumId") long albumId,
			@PathParam("id") long id) {
		return service.getDeletedSubAlbumsAboveId(albumId, id);
	}

}
