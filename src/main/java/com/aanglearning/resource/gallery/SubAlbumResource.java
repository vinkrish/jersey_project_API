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
import com.aanglearning.model.gallery.SubAlbum;
import com.aanglearning.service.gallery.SubAlbumService;

@Path("/subalbum")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubAlbumResource {
	
	SubAlbumService service = new SubAlbumService();
	
	@Secured
	@POST
	public SubAlbum add(SubAlbum subAlbum) {
		return service.add(subAlbum);
	}
	
	@Secured
	@GET
	@Path("{id}/album/{albumId}")
	public List<SubAlbum> getSubAlbumsAboveId(@PathParam("albumId") long albumId, 
			@PathParam("id") long id) {
		return service.getSubAlbumsAboveId(albumId, id);
	}
	
	@Secured
	@GET
	@Path("album/{albumId}")
	public List<SubAlbum> getSubAlbums(@PathParam("albumId") long albumId) {
		return service.getSubAlbums(albumId);
	}
	
	@Secured
	@PUT
	public void update(SubAlbum subAlbum) {
		service.updateSubAlbum(subAlbum);
	}

}
