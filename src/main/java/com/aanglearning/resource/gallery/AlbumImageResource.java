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

@Path("/albumimage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlbumImageResource {
	
	AlbumImageService service = new AlbumImageService();
	
	@Secured
	@POST
	public AlbumImage add(AlbumImage albumImage) {
		return service.add(albumImage);
	}
	
	@Secured
	@GET
	@Path("{id}/school/{schoolId}")
	public List<AlbumImage> getAlbumImagesAboveId(@PathParam("schoolId") long schoolId, 
			@PathParam("id") long id) {
		return service.getAlbumImagesAboveId(schoolId, id);
	}
	
	@Secured
	@GET
	@Path("school/{schoolId}")
	public List<AlbumImage> getAlbumImages(@PathParam("schoolId") long schoolId) {
		return service.getAlbumImages(schoolId);
	}

}
