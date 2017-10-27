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
import com.aanglearning.model.gallery.DeletedSubAlbumImage;
import com.aanglearning.service.gallery.DeletedSubAlbumImageService;

@Path("/deletedsubalbumimage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeletedSubAlbumImageResource {
	
	DeletedSubAlbumImageService service = new DeletedSubAlbumImageService();
	
	@Secured
	@POST
	public DeletedSubAlbumImage add(DeletedSubAlbumImage deletedAlbum) {
		return service.add(deletedAlbum);
	}
	
	@Secured
	@GET
	@Path("subalbum/{subAlbumId}")
	public List<DeletedSubAlbumImage> getDeletedSubAlbumImages(@PathParam("subAlbumId") long subAlbumId) {
		return service.getDeletedSubAlbumImages(subAlbumId);
	}
	
	@Secured
	@GET
	@Path("{id}/subalbum/{subAlbumId}")
	public List<DeletedSubAlbumImage> getDeletedSubAlbumImagesAboveId(@PathParam("subAlbumId") long subAlbumId,
			@PathParam("id") long id) {
		return service.getDeletedSubAlbumImagesAboveId(subAlbumId, id);
	}

}
