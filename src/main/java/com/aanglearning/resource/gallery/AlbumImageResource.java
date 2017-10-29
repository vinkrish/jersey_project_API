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
import com.aanglearning.model.gallery.AlbumImage;
import com.aanglearning.service.gallery.AlbumImageService;

@Path("/ai")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlbumImageResource {
	
	AlbumImageService service = new AlbumImageService();
	
	@Secured
	@POST
	public void add(List<AlbumImage> albumImages) {
		service.add(albumImages);
	}
	
	@Secured
	@GET
	@Path("{id}/album/{albumId}")
	public List<AlbumImage> getAlbumImagesAboveId(@PathParam("albumId") long albumId, 
			@PathParam("id") long id) {
		return service.getAlbumImagesAboveId(albumId, id);
	}
	
	@Secured
	@GET
	@Path("album/{albumId}")
	public List<AlbumImage> getAlbumImages(@PathParam("albumId") long albumId) {
		return service.getAlbumImages(albumId);
	}

}
