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
import com.aanglearning.model.gallery.SubAlbumImage;
import com.aanglearning.service.gallery.SubAlbumImageService;

@Path("/subalbumimage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubAlbumImageResource {
	
	SubAlbumImageService service = new SubAlbumImageService();
	
	@Secured
	@POST
	public SubAlbumImage add(SubAlbumImage subAlbumImage) {
		return service.add(subAlbumImage);
	}
	
	@Secured
	@GET
	@Path("{id}/album/{albumId}")
	public List<SubAlbumImage> getSubAlbumImagesAboveId(@PathParam("albumId") long albumId, 
			@PathParam("id") long id) {
		return service.getSubAlbumImagesAboveId(albumId, id);
	}
	
	@Secured
	@GET
	@Path("album/{albumId}")
	public List<SubAlbumImage> getSubAlbumImages(@PathParam("albumId") long albumId) {
		return service.getSubAlbumImages(albumId);
	}

}
