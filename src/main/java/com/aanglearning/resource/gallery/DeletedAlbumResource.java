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

}
