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
import com.aanglearning.model.gallery.DeletedAlbumImage;
import com.aanglearning.service.gallery.DeletedAlbumImageService;

@Path("/deletedalbumimage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeletedAlbumImageResource {
	
	DeletedAlbumImageService service = new DeletedAlbumImageService();
	
	@Secured
	@POST
	public DeletedAlbumImage add(DeletedAlbumImage deletedAlbum) {
		return service.add(deletedAlbum);
	}
	
	@Secured
	@GET
	@Path("album/{albumId}")
	public List<DeletedAlbumImage> getDeletedAlbumImages(@PathParam("albumId") long albumId) {
		return service.getDeletedAlbumImages(albumId);
	}
	
	@Secured
	@GET
	@Path("{id}/album/{albumId}")
	public List<DeletedAlbumImage> getDeletedAlbumImagesAboveId(@PathParam("albumId") long albumId,
			@PathParam("id") long id) {
		return service.getDeletedAlbumImagesAboveId(albumId, id);
	}

}
