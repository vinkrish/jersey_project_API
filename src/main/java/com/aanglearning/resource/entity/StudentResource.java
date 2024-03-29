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
import com.aanglearning.model.entity.Student;
import com.aanglearning.service.entity.StudentService;

@Path("/student")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
	
	StudentService studentService = new StudentService();

	@Secured
	@GET
	@Path("class/{classId}")
	public List<Student> getStudentClass(@PathParam("classId") long classId) {
		return studentService.getStudentClass(classId);
	}
	
	@Secured
	@GET
	@Path("section/{sectionId}")
	public List<Student> getStudentSection(@PathParam("sectionId") long sectionId) {
		return studentService.getStudentSection(sectionId);
	}
	
	@Secured
	@GET
	@Path("{studentId}")
	public Student getStudent(@PathParam("studentId") long studentId) {
		return studentService.getStudent(studentId);
	}
	
	@Secured
	@POST
	public Student add(Student student) {
		return studentService.add(student);
	}
	
	@Secured
	@PUT
	@Path("{studentId}")
	public void update(Student student) {
		studentService.update(student);
	}
	
	@Secured
	@DELETE
	@Path("{studentId}")
	public void delete(@PathParam("studentId") long studentId) {
		studentService.delete(studentId);
	}
	
	public List<Student> getStudents(String query) {
		return studentService.getStudentList(query);
	}
	
	
	public List<Student> getSectionGroupUsers(long groupId, long sectionId) {
		return studentService.getSectionGroupUsers(groupId, sectionId);
	}
	
	public List<Student> getClassGroupUsers(long groupId, long classId) {
		return studentService.getClassGroupUsers(groupId, classId);
	}
	
}
