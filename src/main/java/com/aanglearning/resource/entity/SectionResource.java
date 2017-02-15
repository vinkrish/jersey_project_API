package com.aanglearning.resource.entity;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.entity.Section;
import com.aanglearning.service.entity.SectionService;

@Path("/section")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SectionResource {

	SectionService sectionService = new SectionService();

	@Secured
	@GET
	@Path("class/{classId}")
	public List<Section> getSectionList(@PathParam("classId") long classId) {
		return sectionService.getSectionList(classId);
	}

	@Secured
	@POST
	public Section add(Section section) {
		return sectionService.add(section);
	}
	
	@Secured
	@PUT
	@Path("/{sectionId}")
	public void update(Section section) {
		sectionService.update(section);
	}
	
	@Secured
	@DELETE
	@Path("/{sectionId}")
	public void delete(@PathParam("sectionId") long sectionId) {
		sectionService.delete(sectionId);
	}

}
