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
	@PUT
	public void update(Album album) {
		service.updateAlbum(album);
	}
}
