package com.project.guldu.resources;

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

import com.project.guldu.model.Student;
import com.project.guldu.service.StudentService;

@Path("/student")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
	
	StudentService studentService = new StudentService();

	@GET
	@Path("section/{sectionId}")
	public List<Student> getStudentSection(@PathParam("sectionId") long sectionId) {
		return studentService.getStudentSection(sectionId);
	}
	
	@GET
	@Path("class/{classId}")
	public List<Student> getStudentClass(@PathParam("classId") long classId) {
		return studentService.getStudentClass(classId);
	}

	@POST
	@Path("list")
	public void addStudent(String studentStr) {
		studentService.addStudent(studentStr);
	}
	
	@POST
	public Student add(Student student) {
		return studentService.add(student);
	}
	
	@PUT
	@Path("/{studentId}")
	public void update(Student student) {
		studentService.update(student);
	}
	
	@DELETE
	@Path("/{studentId}")
	public void delete(@PathParam("studentId") long studentId) {
		studentService.delete(studentId);
	}
}
