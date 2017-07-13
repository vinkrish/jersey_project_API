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
import com.aanglearning.model.entity.SubjectTeacher;
import com.aanglearning.service.entity.SubjectTeacherService;

@Path("/subjectteacher")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectTeacherResource {
	
	SubjectTeacherService subjectTeacherService = new SubjectTeacherService();

	@Secured
	@GET
	@Path("section/{sectionId}")
	public List<SubjectTeacher> getSubjectTeacher (@PathParam("sectionId") long sectionId) {
		return subjectTeacherService.getSubjectTeacher(sectionId);
	}

	@Secured
	@POST
	public SubjectTeacher add(SubjectTeacher subjectTeacher) {
		return subjectTeacherService.add(subjectTeacher);
	}
	
	@Secured
	@PUT
	@Path("/{teacherId}")
	public void update(SubjectTeacher subjectTeacher) {
		subjectTeacherService.update(subjectTeacher);
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		subjectTeacherService.delete(id);
	}
	
	public List<SubjectTeacher> getSubjectTeacherByQuery(String query) {
		return subjectTeacherService.getSubjectTeacherList(query);
	}
	
}
