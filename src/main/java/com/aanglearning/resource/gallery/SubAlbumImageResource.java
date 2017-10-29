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

@Path("/sai")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubAlbumImageResource {
	
	SubAlbumImageService service = new SubAlbumImageService();
	
	@Secured
	@POST
	public void add(List<SubAlbumImage> subAlbumImages) {
		service.add(subAlbumImages);
	}
	
	@Secured
	@GET
	@Path("{id}/subalbum/{subAlbumId}")
	public List<SubAlbumImage> getSubAlbumImagesAboveId(@PathParam("subAlbumId") long subAlbumId, 
			@PathParam("id") long id) {
		return service.getSubAlbumImagesAboveId(subAlbumId, id);
	}
	
	@Secured
	@GET
	@Path("subalbum/{subAlbumId}")
	public List<SubAlbumImage> getSubAlbumImages(@PathParam("subAlbumId") long subAlbumId) {
		return service.getSubAlbumImages(subAlbumId);
	}

}
