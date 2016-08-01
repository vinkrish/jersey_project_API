package com.project.guldu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.project.guldu.model.Student;

public class StudentService {
	Statement stmt = null;

	public StudentService() {
		try {
			stmt = JDBC.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Student> getStudentSection(long sectionId) {
		String query = "select * from student where SectionId = " + sectionId;
		return getStudentList(query);
	}
	
	public List<Student> getStudentClass (long classId) {
		String query = "select * from student where ClassId = " + classId;
		return getStudentList(query);
	}
	
	public List<Student> getStudentList(String query) {
		List<Student> studentList = new ArrayList<Student>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				Student student = new Student();
				student.setId(rs.getLong("Id"));
				student.setStudentName(rs.getString("StudentName"));
				student.setSchoolId(rs.getLong("SchoolId"));
				student.setSectionId(rs.getLong("SectionId"));
				student.setAdmissionNo(rs.getString("AdmissionNo"));
				student.setRollNo(rs.getInt("RollNo"));
				student.setUsername(rs.getString("Username"));
				student.setPassword(rs.getString("Password"));
				student.setImage(rs.getString("Image"));
				student.setFatherName(rs.getString("FatherName"));
				student.setMotherName(rs.getString("MotherName"));
				student.setDateOfBirth(rs.getString("DateOfBirth"));
				student.setGender(rs.getString("Gender"));
				student.setEmail(rs.getString("Email"));
				student.setMobile1(rs.getString("Mobile1"));
				student.setMobile2(rs.getString("Mobile2"));
				student.setStreet(rs.getString("Street"));
				student.setCity(rs.getString("City"));
				student.setDistrict(rs.getString("District"));
				student.setState(rs.getString("State"));
				student.setPincode(rs.getString("Pincode"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
	}
	
	public void addStudent (String studentStr) {
		JSONArray studentArray = new JSONArray(studentStr);
		for (int i = 0; i < studentArray.length(); i++) {
			JSONObject studentJson = studentArray.getJSONObject(i);
			Gson gson = new Gson();
			Student student = gson.fromJson(studentJson.toString(), Student.class);
			try {
				String query = "insert into student(Id, StudentName, SectionId, "
						+ "AdmissionNo, RollNo, Username, Password, Image, FatherName, MotherName, DateOfBirth, "
						+ "Gender, Email, Mobile1, Mobile2, Street, City, District, State, Pincode) "
						+ "values (" 
						+ student.getId() + ",'" 
						+ student.getStudentName() + "',"
						+ student.getSectionId()  + ",'"
						+ student.getAdmissionNo() + "',"
						+ student.getRollNo() + ",'"
						+ student.getUsername() + "','"
						+ student.getPassword() + "','"
						+ student.getImage() + "','"
						+ student.getFatherName() + "','"
						+ student.getMotherName() + "','"
						+ student.getDateOfBirth() + "','"
						+ student.getGender() + "','"
						+ student.getEmail() + "','"
						+ student.getMobile1() + "','"
						+ student.getMobile2() + "','"
						+ student.getStreet() + "','"
						+ student.getCity() + "','"
						+ student.getDistrict() + "','"
						+ student.getState() + "','"
						+ student.getPincode() + "')";
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Student add(Student student) {
		try {
			String query = "insert into student(StudentId, StudentName, SchoolId, SectionId, "
					+ "AdmissionNo, RollNo, Username, Password, Image, FatherName, MotherName, DateOfBirth, "
					+ "Gender, Email, Mobile1, Mobile2, Street, City, District, State, Pincode) "
					+ "values (" 
					+ student.getId() + ",'" 
					+ student.getStudentName() + "',"
					+ student.getSchoolId() + ","
					+ student.getSectionId()  + ",'"
					+ student.getAdmissionNo() + "',"
					+ student.getRollNo() + ",'"
					+ student.getUsername() + "','"
					+ student.getPassword() + "','"
					+ student.getImage() + "','"
					+ student.getFatherName() + "','"
					+ student.getMotherName() + "','"
					+ student.getDateOfBirth() + "','"
					+ student.getGender() + "','"
					+ student.getEmail() + "','"
					+ student.getMobile1() + "','"
					+ student.getMobile2() + "','"
					+ student.getStreet() + "','"
					+ student.getCity() + "','"
					+ student.getDistrict() + "','"
					+ student.getState() + "','"
					+ student.getPincode() + "')";
			long pk = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			student.setId(pk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	public void update(Student student) {
		try {
			String query = "update student set  StudentName = '" + student.getStudentName() 
			+ "', AdmissionNo = '" + student.getAdmissionNo() 
			+ "', RollNo = " + student.getRollNo() 
			+ ", Username = '" + student.getUsername() 
			+ "', Password = '" + student.getPassword() 
			+ "', FatherName = '" + student.getFatherName() 
			+ "', MotherName = '" + student.getMotherName() 
			+ "', DateOfBirth = '" + student.getDateOfBirth() 
			+ "', Gender = '" + student.getGender() 
			+ "', Email = '" + student.getEmail() 
			+ "', Mobile1 = '" + student.getMobile1() 
			+ "', Mobile2 = '" + student.getMobile2() 
			+ "', Street = '" + student.getStreet() 
			+ "', City = '" + student.getCity() 
			+ "', District = '" + student.getDistrict() 
			+ "', State = '" + student.getState() 
			+ "', Pincode = '" + student.getPincode() 
			+ "' where Id=" + student.getId();
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long studentId){
		try {
			String query = "delete from student where Id=" + studentId;
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
